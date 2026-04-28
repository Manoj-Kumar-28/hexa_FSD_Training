import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import axios from 'axios'
import OwnerNavbar from './nav-bar'
import './css/add-car.css'

function AddCar() {
    const navigate = useNavigate()
    const [carNumber, setCarNumber] = useState('')
    const [brand, setBrand] = useState('')
    const [model, setModel] = useState('')
    const [pricePerDay, setPricePerDay] = useState('')
    const [location, setLocation] = useState('')
    const [fuelType, setFuelType] = useState('')
    const [color, setColor] = useState('')
    const [seats, setSeats] = useState('')

    const [errors, setErrors] = useState({})
    const [successMsg, setSuccessMsg] = useState('')
    const [errorMsg, setErrorMsg] = useState('')
    const [isSubmitting, setIsSubmitting] = useState(false)
    const [showLocationList, setShowLocationList] = useState(false)

    const famousCities = [
        "Mumbai", "Delhi", "Bangalore", "Hyderabad", "Ahmedabad",
        "Chennai", "Kolkata", "Pune", "Jaipur", "Lucknow",
        "Chandigarh", "Kochi", "Goa", "Indore"
    ]

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

    const brandModels = {
        "Maruti Suzuki": ["Swift", "Baleno", "Brezza", "Ertiga", "Dzire", "Grand Vitara"],
        "Hyundai": ["Creta", "Venue", "Verna", "i20", "i10 Nios", "Alcazar"],
        "Tata": ["Nexon", "Harrier", "Safari", "Punch", "Tiago", "Altroz"],
        "Mahindra": ["Thar", "XUV700", "Scorpio N", "XUV300", "Bolero", "XUV400"],
        "Toyota": ["Fortuner", "Innova Crysta", "Urban Cruiser", "Glanza", "Camry", "Innova Hycross"]
    }

    const brands = Object.keys(brandModels)
    const models = brand ? brandModels[brand] || [] : []
    const [file, setFile] = useState(undefined)
    const [carImage, setCarImage] = useState(undefined)

    const addApi = "http://localhost:8080/api/owner/add"

    // Validation
    const validate = () => {
        const newErrors = {}

        if (!carNumber.trim()) {
            newErrors.carNumber = "Car number is required"
        } else if (!/^\d{6,12}$/.test(carNumber.trim())) {
            newErrors.carNumber = "Enter a valid car number (6-12 digits)"
        }

        if (!brand) newErrors.brand = "Please select a brand"
        if (!model) newErrors.model = "Please select a model"

        if (!pricePerDay) {
            newErrors.pricePerDay = "Price is required"
        } else if (isNaN(pricePerDay) || Number(pricePerDay) <= 0) {
            newErrors.pricePerDay = "Enter a valid price greater than 0"
        }

        if (!location.trim()) newErrors.location = "Location is required"
        if (!fuelType) newErrors.fuelType = "Please select a fuel type"
        if (!color.trim()) newErrors.color = "Color is required"

        if (!seats) {
            newErrors.seats = "Seats is required"
        } else if (isNaN(seats) || Number(seats) < 2 || Number(seats) > 10) {
            newErrors.seats = "Seats must be between 2 and 10"
        }

        setErrors(newErrors)
        return Object.keys(newErrors).length === 0
    }

    const addNewCar = async (e) => {
        e.preventDefault()
        setErrorMsg('')
        setSuccessMsg('')

        if (!validate()) return

        setIsSubmitting(true)

        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        }
        try {


            const carResponse = await axios.post(addApi,
                {
                    "carNumber": carNumber,
                    "brand": brand,
                    "model": model,
                    "pricePerDay": pricePerDay,
                    "location": location,
                    "fuelType": fuelType,
                    "color": color,
                    "seats": seats
                }, config)
            const carId = carResponse.data.id;

            if (!file) {
                alert("Please select image")
                setIsSubmitting(false)
                return
            }

            const api = `http://localhost:8080/api/document/upload?carId=${carId}`

            const formData = new FormData()
            formData.append("file", file)

            const response = await axios.post(api, formData, config)

            let path = "uploads/"
            setCarImage(path + response.data.carImage)
            setSuccessMsg("Car added successfully!")

            // Reset form
            setCarNumber(''); setBrand(''); setModel('');
            setPricePerDay(''); setLocation(''); setFuelType('');
            setColor(''); setSeats(''); setErrors({}); setFile(null); setCarImage(null)
        } catch (err) {

            setErrorMsg(err.response?.data?.message || "Failed to add car")

        } finally {
            setIsSubmitting(false)
        }
    }



    return (
        <div className="add-car-page">
            <OwnerNavbar />

            <div className="add-car-container">
                <button className="back-btn" onClick={() => navigate('/owner-dashboard')}>
                    &larr; Back to Dashboard
                </button>

                <div className="add-car-card">
                    <div className="add-car-header">
                        <i className="bi bi-car-front-fill header-icon"></i>
                        <h2>Add New Car</h2>
                        <p>Fill in the details to list your car for rental</p>
                    </div>

                    {errorMsg && (
                        <div className="form-alert error">
                            <i className="bi bi-exclamation-circle-fill"></i> {errorMsg}
                        </div>
                    )}
                    {successMsg && (
                        <div className="form-alert success">
                            <i className="bi bi-check-circle-fill"></i> {successMsg}
                        </div>
                    )}

                    <form className="add-car-form" onSubmit={addNewCar}>
                        <div className="form-row">
                            <div className="form-field">
                                <label><i className="bi bi-hash"></i> Car Number</label>
                                <input
                                    type="text"
                                    placeholder="e.g. 12345678"
                                    value={carNumber}
                                    onChange={(e) => setCarNumber(e.target.value)}
                                />
                                {errors.carNumber && <span className="field-error">{errors.carNumber}</span>}
                            </div>
                            <div className="form-field">
                                <label><i className="bi bi-geo-alt-fill"></i> Location</label>
                                <div className="dropdown-container">
                                    <input
                                        type="text"
                                        placeholder="e.g. Hyderabad"
                                        value={location}
                                        onFocus={() => setShowLocationList(true)}
                                        onBlur={() => setTimeout(() => setShowLocationList(false), 200)}
                                        onChange={(e) => setLocation(e.target.value)}
                                    />
                                    {showLocationList && (
                                        <ul className="suggestions-list">
                                            {getFilteredCities(location).map(city => (
                                                <li key={city} onMouseDown={() => {
                                                    setLocation(city)
                                                    setShowLocationList(false)
                                                }}>
                                                    {city}
                                                </li>
                                            ))}
                                            {getFilteredCities(location).length === 0 && (
                                                <li className="no-results">No cities found</li>
                                            )}
                                        </ul>
                                    )}
                                </div>
                                {errors.location && <span className="field-error">{errors.location}</span>}
                            </div>
                        </div>

                        <div className="form-row">
                            <div className="form-field">
                                <label><i className="bi bi-building"></i> Brand</label>
                                <select value={brand} onChange={(e) => { setBrand(e.target.value); setModel(''); }}>
                                    <option value="">Select Brand</option>
                                    {brands.map(b => <option key={b} value={b}>{b}</option>)}
                                </select>
                                {errors.brand && <span className="field-error">{errors.brand}</span>}
                            </div>
                            <div className="form-field">
                                <label><i className="bi bi-car-front"></i> Model</label>
                                <select value={model} onChange={(e) => setModel(e.target.value)} disabled={!brand}>
                                    <option value="">Select Model</option>
                                    {models.map(m => <option key={m} value={m}>{m}</option>)}
                                </select>
                                {errors.model && <span className="field-error">{errors.model}</span>}
                            </div>
                        </div>

                        <div className="form-row">
                            <div className="form-field">
                                <label><i className="bi bi-currency-rupee"></i> Price Per Day</label>
                                <input
                                    type="number"
                                    placeholder="e.g. 2500"
                                    value={pricePerDay}
                                    onChange={(e) => setPricePerDay(e.target.value)}
                                />
                                {errors.pricePerDay && <span className="field-error">{errors.pricePerDay}</span>}
                            </div>
                            <div className="form-field">
                                <label><i className="bi bi-fuel-pump-fill"></i> Fuel Type</label>
                                <select value={fuelType} onChange={(e) => setFuelType(e.target.value)}>
                                    <option value="">Select Fuel Type</option>
                                    <option value="PETROL">PETROL</option>
                                    <option value="DIESEL">DIESEL</option>
                                    <option value="ELECTRIC">ELECTRIC</option>
                                    <option value="CNG">CNG</option>
                                </select>
                                {errors.fuelType && <span className="field-error">{errors.fuelType}</span>}
                            </div>
                        </div>

                        <div className="form-row">
                            <div className="form-field">
                                <label><i className="bi bi-palette-fill"></i> Color</label>
                                <input
                                    type="text"
                                    placeholder="e.g. White"
                                    value={color}
                                    onChange={(e) => setColor(e.target.value)}
                                />
                                {errors.color && <span className="field-error">{errors.color}</span>}
                            </div>
                            <div className="form-field">
                                <label><i className="bi bi-people-fill"></i> Seats</label>
                                <input
                                    type="number"
                                    placeholder="e.g. 5"
                                    value={seats}
                                    onChange={(e) => setSeats(e.target.value)}
                                />
                                {errors.seats && <span className="field-error">{errors.seats}</span>}
                            </div>

                        </div>
                        <div className="form-field">
                            <label><i className="bi bi-image-fill"></i> Car Image</label>

                            <label>Upload Car Image: </label>
                            <input type="file" onChange={(e) => setFile(e.target.files[0])}></input>

                        </div>

                        <button type="submit" className="submit-car-btn" disabled={isSubmitting}>
                            {isSubmitting ? (
                                <span><i className="bi bi-arrow-repeat spin"></i> Adding Car...</span>
                            ) : (
                                <span><i className="bi bi-plus-circle"></i> Add Car</span>
                            )}
                        </button>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default AddCar
