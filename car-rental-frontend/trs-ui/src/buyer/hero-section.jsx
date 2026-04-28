import { useState } from "react";
import { useNavigate } from "react-router-dom";

function HeroSection() {

    const navigate = useNavigate();
    const [pickupLocation, setPickupLocation] = useState("");
    const [dropLocation, setDropLocation] = useState("");
    const [pickupDate, setPickupDate] = useState("");
    const [dropDate, setDropDate] = useState("");

    const [showPickupList, setShowPickupList] = useState(false);
    const [showDropList, setShowDropList] = useState(false);

    const famousCities = [
        "Mumbai", "Delhi", "Bangalore", "Hyderabad", "Ahmedabad",
        "Chennai", "Kolkata", "Pune", "Jaipur", "Lucknow",
        "Chandigarh", "Kochi", "Goa", "Indore"
    ];

    const getFilteredCities = (input) => {
        if (!input) return famousCities.sort();
        return famousCities
            .filter(city => city.toLowerCase().includes(input.toLowerCase()))
            .sort((a, b) => {
                // Prioritize cities starting with the input
                const aStarts = a.toLowerCase().startsWith(input.toLowerCase());
                const bStarts = b.toLowerCase().startsWith(input.toLowerCase());
                if (aStarts && !bStarts) return -1;
                if (!aStarts && bStarts) return 1;
                return a.localeCompare(b);
            });
    };

    const handleSearch = (e) => {
        e.preventDefault();

        // Redirect to login if not authenticated
        if (!localStorage.getItem('token')) {
            navigate('/login');
            return;
        }

        // Save to localStorage for auto-filling booking form later
        localStorage.setItem("rental_pickupLocation", pickupLocation);
        localStorage.setItem("rental_dropLocation", dropLocation);
        localStorage.setItem("rental_pickupDate", pickupDate);
        localStorage.setItem("rental_dropDate", dropDate);

        const params = new URLSearchParams();
        if (pickupLocation) params.set("location", pickupLocation);
        if (pickupDate) params.set("pickupDate", pickupDate);
        if (dropDate) params.set("dropDate", dropDate);
        navigate(`/browse?${params.toString()}`);
    };

    return (
        <div className="hero-section">
            <div className="booking-form-wrapper">
                <div className="booking-card">
                    <h2 className="booking-title">Book Your Ride</h2>
                    <form className="booking-form" onSubmit={handleSearch}>

                        <div className="form-group dropdown-container">
                            <label>Pickup Location</label>
                            <input
                                type="text"
                                className="booking-input"
                                placeholder="Search city (e.g. Mumbai)"
                                required="required"
                                value={pickupLocation}
                                onFocus={() => setShowPickupList(true)}
                                onBlur={() => setTimeout(() => setShowPickupList(false), 200)}
                                onChange={(e) => setPickupLocation(e.target.value)}
                            />
                            {showPickupList && (
                                <ul className="suggestions-list">
                                    {getFilteredCities(pickupLocation).map(city => (
                                        <li key={city} onMouseDown={() => {
                                            setPickupLocation(city);
                                            setShowPickupList(false);
                                        }}>
                                            {city}
                                        </li>
                                    ))}
                                    {getFilteredCities(pickupLocation).length === 0 && (
                                        <li className="no-results">No cities found</li>
                                    )}
                                </ul>
                            )}
                        </div>

                        <div className="form-group dropdown-container">
                            <label>Drop Location</label>
                            <input
                                type="text"
                                className="booking-input"
                                placeholder="Search city (e.g. Delhi)"
                                required="required"
                                value={dropLocation}
                                onFocus={() => setShowDropList(true)}
                                onBlur={() => setTimeout(() => setShowDropList(false), 200)}
                                onChange={(e) => setDropLocation(e.target.value)}
                            />
                            {showDropList && (
                                <ul className="suggestions-list">
                                    {getFilteredCities(dropLocation).map(city => (
                                        <li key={city} onMouseDown={() => {
                                            setDropLocation(city);
                                            setShowDropList(false);
                                        }}>
                                            {city}
                                        </li>
                                    ))}
                                    {getFilteredCities(dropLocation).length === 0 && (
                                        <li className="no-results">No cities found</li>
                                    )}
                                </ul>
                            )}
                        </div>

                        <div className="date-row">
                            <div className="form-group">
                                <label>Pickup Date</label>
                                <input type="date" className="booking-input" required
                                    value={pickupDate} onChange={(e) => setPickupDate(e.target.value)} />
                            </div>
                            <div className="form-group">
                                <label>Drop Date</label>
                                <input type="date" className="booking-input" required
                                    value={dropDate} onChange={(e) => setDropDate(e.target.value)} />
                            </div>
                        </div>

                        <button type="submit" className="search-btn">Search Cars</button>
                    </form>
                </div>
            </div>

            <div className="hero-text-wrapper">
                <h1 className="hero-heading">DRIVE <span>ANYTIME</span><br />ANYWHERE</h1>
                <p className="hero-subheading">
                    With no commitment, unlimited options and hassle-free booking, your road to adventure is just a few clicks away!
                </p>
                <img src="/hero_cars.png" alt="Fleet of modern cars" className="hero-image" />
            </div>
        </div>
    );
}

export default HeroSection;


