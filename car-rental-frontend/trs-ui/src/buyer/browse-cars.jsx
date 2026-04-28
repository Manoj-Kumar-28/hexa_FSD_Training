import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate, useSearchParams } from 'react-router-dom';
import BuyerNavbar from './buyerNavbar';
import BuyerFooter from './buyer-footer';
import './css/buyer-dashboard.css'; // Common styles
import './css/browse-cars.css'; // Specific layout

function BrowseCars() {

    const navigate = useNavigate();
    const [searchParams] = useSearchParams();

    // Redirect to login if not authenticated
    useEffect(() => {
        if (!localStorage.getItem('token')) {
            navigate('/login');
        }
    }, []);

    const [cars, setCars] = useState([]);
    const [errMsg, setErrMsg] = useState(undefined);
    const [sortBy, setSortBy] = useState('');

    // Pagination state
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [totalRecords, setTotalRecords] = useState(0);

    // Filter state
    const [fuelType, setFuelType] = useState('');
    const [seats, setSeats] = useState('');
    const [brandFilter, setBrandFilter] = useState('');
    const [modelFilter, setModelFilter] = useState('');

    // Brand -> Model mapping
    const brandModels = {
        "Maruti Suzuki": ["Swift", "Baleno", "Brezza", "Ertiga", "Wagon R", "Alto", "Dzire", "Ciaz"],
        "Hyundai": ["i20", "Creta", "Venue", "Verna", "Tucson", "Alcazar", "Grand i10"],
        "Tata": ["Nexon", "Harrier", "Safari", "Punch", "Altroz", "Tiago", "Tigor"],
        "Mahindra": ["Thar", "XUV700", "Scorpio N", "XUV300", "Bolero", "XUV400"],
        "Toyota": ["Fortuner", "Innova Crysta", "Urban Cruiser", "Glanza", "Camry", "Innova Hycross"]
    };
    const brands = Object.keys(brandModels);
    const models = brandFilter ? brandModels[brandFilter] || [] : [];

    // Reading location and dates from search params (from home page search)
    const initialLocation = searchParams.get('location') || '';
    const initialPickupDate = searchParams.get('pickupDate') || '';
    const initialDropDate = searchParams.get('dropDate') || '';

    const [locationFilter, setLocationFilter] = useState(initialLocation);
    const [pickupDate, setPickupDate] = useState(initialPickupDate);
    const [dropDate, setDropDate] = useState(initialDropDate);
    const [isFiltering, setIsFiltering] = useState(!!(initialLocation || initialPickupDate || initialDropDate));
    const [showLocationList, setShowLocationList] = useState(false);

    const famousCities = [
        "Mumbai", "Delhi", "Bangalore", "Hyderabad", "Ahmedabad",
        "Chennai", "Kolkata", "Pune", "Jaipur", "Lucknow",
        "Chandigarh", "Kochi", "Goa", "Indore"
    ];

    const getFilteredCities = (input) => {
        if (!input) return famousCities.sort()
        return famousCities
            .filter(city => city.toLowerCase().includes(input.toLowerCase()))
            .sort((a, b) => {
                const aStarts = a.toLowerCase().startsWith(input.toLowerCase())
                const bStarts = b.toLowerCase().startsWith(input.toLowerCase())
                if (aStarts && !bStarts) return -1
                if (!aStarts && bStarts) return 1
                return a.localeCompare(b)
            })
    }

    const getAllCarsApi = `http://localhost:8080/api/buyer/get-all?page=${currentPage}&size=6`;
    const filterApi = `http://localhost:8080/api/buyer/get/filter`;
    const placeholderImage = "/car_card_placeholder.png";

    const config = {
        headers: {
            "Authorization": 'Bearer ' + localStorage.getItem('token')
        }
    };

    // Fetch all cars (no filter)
    const fetchAllCars = async () => {
        try {
            const response = await axios.get(getAllCarsApi, config);
            setCars(response.data.data);
            setTotalPages(response.data.totalPages);
            setTotalRecords(response.data.totalRecords);
            setErrMsg(undefined);
        } catch (err) {
            setErrMsg("Error in fetching vehicles.");
        }
    };

    // Fetch filtered cars from backend
    const fetchFilteredCars = async () => {
        try {
            let carList = [];

            // If dates are present, use the new availability API
            if (pickupDate && dropDate) {
                const availabilityApi = `http://localhost:8080/api/buyer/get/cars/available/v2?startDate=${pickupDate}&endDate=${dropDate}`;
                const response = await axios.get(availabilityApi, config);
                carList = response.data; // Assuming it returns an array of cars

                // Client-side filtering for other criteria
                if (locationFilter) {
                    carList = carList.filter(car =>
                        car.location && car.location.toLowerCase().includes(locationFilter.toLowerCase())
                    );
                }
                if (brandFilter) {
                    carList = carList.filter(car => car.brand === brandFilter);
                }
                if (modelFilter) {
                    carList = carList.filter(car => car.model === modelFilter);
                }
                if (fuelType) {
                    carList = carList.filter(car => car.fuelType === fuelType);
                }
                if (seats) {
                    carList = carList.filter(car => car.seats === Number(seats));
                }
            } else {
                // Otherwise use the standard filter API
                const filterData = {
                    location: locationFilter || "",
                    brand: brandFilter || "",
                    model: modelFilter || "",
                    fuelType: fuelType || "",
                    seats: seats ? Number(seats) : 0
                };
                const response = await axios.post(filterApi, filterData, config);
                carList = response.data;
            }

            setCars(carList);
            setErrMsg(undefined);
        } catch (err) {
            setErrMsg("Error in filtering vehicles.");
        }
    };

    // Apply filters
    const applyFilters = () => {
        const hasFilter = locationFilter || brandFilter || modelFilter || fuelType || seats;
        if (hasFilter) {
            setIsFiltering(true);
            setCurrentPage(0);
            fetchFilteredCars();
        } else {
            clearFilters();
        }
    };

    // Clear filters
    const clearFilters = () => {
        setBrandFilter(''); setModelFilter(''); setFuelType(''); setSeats(''); setLocationFilter('');
        setPickupDate(''); setDropDate('');
        setIsFiltering(false);
        setCurrentPage(0);
    };

    useEffect(() => {
        if (!isFiltering) {
            fetchAllCars();
        } else {
            fetchFilteredCars();
        }
    }, [currentPage, isFiltering]);

    // getSortedCars logic...

    const getSortedCars = () => {
        let sorted = [...cars];
        if (sortBy === 'priceLow') {
            sorted.sort((a, b) => a.pricePerDay - b.pricePerDay);
        } else if (sortBy === 'priceHigh') {
            sorted.sort((a, b) => b.pricePerDay - a.pricePerDay);
        } else if (sortBy === 'newest') {
            sorted.sort((a, b) => b.id - a.id);
        }
        return sorted;
    };

    return (
        <div className="dashboard-container">
            <BuyerNavbar />

            <div className="browse-header">
                <h2>Explore Our Fleet</h2>
                <p>Find the perfect vehicle for your next adventure.</p>
            </div>

            <div className="browse-content">
                {/**Filter API pending */}
                <div className="filter-sidebar">
                    <h4 className="filter-title">Filters</h4>

                    <div className="filter-group dropdown-container">
                        <label>Location</label>
                        <input type="text" className="filter-input" placeholder="Enter city..."
                            value={locationFilter}
                            onFocus={() => setShowLocationList(true)}
                            onBlur={() => setTimeout(() => setShowLocationList(false), 200)}
                            onChange={(e) => setLocationFilter(e.target.value)} />
                        {showLocationList && (
                            <ul className="suggestions-list">
                                {getFilteredCities(locationFilter).map(city => (
                                    <li key={city} onMouseDown={() => {
                                        setLocationFilter(city);
                                        setShowLocationList(false);
                                    }}>
                                        {city}
                                    </li>
                                ))}
                                {getFilteredCities(locationFilter).length === 0 && (
                                    <li className="no-results">No cities found</li>
                                )}
                            </ul>
                        )}
                    </div>

                    <div className="filter-group">
                        <label>Brand</label>
                        <select className="filter-input" value={brandFilter} onChange={(e) => { setBrandFilter(e.target.value); setModelFilter(''); }}>
                            <option value="">All Brands</option>
                            {brands.map(b => <option key={b} value={b}>{b}</option>)}
                        </select>
                    </div>

                    <div className="filter-group">
                        <label>Model</label>
                        <select className="filter-input" value={modelFilter} onChange={(e) => setModelFilter(e.target.value)} disabled={!brandFilter}>
                            <option value="">All Models</option>
                            {models.map(m => <option key={m} value={m}>{m}</option>)}
                        </select>
                    </div>

                    <div className="filter-group">
                        <label>Fuel Type</label>
                        <select className="filter-input" value={fuelType} onChange={(e) => setFuelType(e.target.value)}>
                            <option value="">All Types</option>
                            <option value="PETROL">PETROL</option>
                            <option value="DIESEL">DIESEL</option>
                            <option value="ELECTRIC">ELECTRIC</option>
                        </select>
                    </div>

                    <div className="filter-group">
                        <label>Seats</label>
                        <select className="filter-input" value={seats} onChange={(e) => setSeats(e.target.value)}>
                            <option value="">All Seats</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="7">7</option>
                        </select>
                    </div>

                    <button className="apply-filter-btn" onClick={applyFilters}>Apply Filters</button>
                    <button className="clear-filter-btn" onClick={clearFilters}>Clear Filters</button>
                </div>

                {/* Main Vehicle Grid */}
                <div className="browse-grid-container">
                    <div className="grid-header">
                        <div className="sort-container">
                            <label>Sort By:</label>
                            <select className="sort-select" value={sortBy} onChange={(e) => setSortBy(e.target.value)}>
                                <option value="">Default</option>
                                <option value="priceLow">Price: Low to High</option>
                                <option value="priceHigh">Price: High to Low</option>
                                <option value="newest">Newest First</option>
                            </select>
                        </div>
                        {totalRecords > 0 && !isFiltering && (
                            <p className="total-results">{totalRecords} vehicles available</p>
                        )}
                    </div>

                    {errMsg && <p className="error-message">{errMsg}</p>}

                    {cars.length === 0 && !errMsg && (
                        <div className="no-cars-message">
                            <h3>No vehicles found</h3>
                            <p>Try adjusting your filters or clearing them to see all available cars.</p>
                        </div>
                    )}

                    <div className="browse-vehicle-grid">
                        {getSortedCars().map((car, index) => (
                            <div className="vehicle-card" key={index}>
                                <div className="vehicle-image-container">
                                    <img
                                        src={
                                            car.carImage
                                                ? `/uploads/${car.carImage}`
                                                : "/car_card_placeholder.png"
                                        }
                                        alt={`${car.brand} ${car.model}`}
                                        className="main-car-image"
                                    />
                                </div>
                                <div className="vehicle-info">
                                    <h4 className="vehicle-name">{car.brand} {car.model}</h4>
                                    <p className="vehicle-price">{car.pricePerDay} rs/day</p>
                                    <button className="view-details-btn" onClick={() => navigate(`/car-details/${car.id}`)}>
                                        View Details
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>

                    {/* Pagination Controls - only when not filtering */}
                    {!isFiltering && (
                        <div className="pagination-container">
                            <button
                                className="pagination-btn"
                                disabled={currentPage === 0}
                                onClick={() => setCurrentPage(p => p - 1)}
                            >
                                &lt; Prev
                            </button>

                            <div className="pagination-numbers">
                                {Array.from({ length: totalPages }, (_, i) => (
                                    <button
                                        key={i}
                                        className={`page-num-btn ${currentPage === i ? 'active' : ''}`}
                                        onClick={() => setCurrentPage(i)}
                                    >
                                        {i + 1}
                                    </button>
                                ))}
                            </div>

                            <button
                                className="pagination-btn"
                                disabled={currentPage >= totalPages - 1}
                                onClick={() => setCurrentPage(p => p + 1)}
                            >
                                Next &gt;
                            </button>
                        </div>
                    )}
                </div>
            </div>

            <BuyerFooter />
        </div>
    );
}

export default BrowseCars;
