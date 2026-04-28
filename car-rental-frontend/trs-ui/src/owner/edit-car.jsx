import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import OwnerNavbar from './nav-bar';
import './css/add-car.css';

function EditCar() {

    const [carNumber, setCarNumber] = useState("");
    const [brand, setBrand] = useState("");
    const [model, setModel] = useState("");
    const [pricePerDay, setPricePerDay] = useState("");
    const [location, setLocation] = useState("");
    const [color, setColor] = useState("");
    const [fuelType, setFuelType] = useState("");
    const [seats, setSeats] = useState("");

    const [successMsg, setSuccessMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const { id } = useParams();
    const navigate = useNavigate();



    const updateApi = `http://localhost:8080/api/owner/update/${id}`;
    const getCarApi = `http://localhost:8080/api/owner/get/${id}`;

    //fetching existing car details
    useEffect(() => {
        const fetchCar = async () => {
            try {
                const config = {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token")
                    }
                };
                const res = await axios.get(getCarApi, config);
                const data = res.data;

                setCarNumber(data.carNumber);
                setBrand(data.brand);
                setModel(data.model);
                setPricePerDay(data.pricePerDay);
                setLocation(data.location);
                setColor(data.color);
                setFuelType(data.fuelType);
                setSeats(data.seats);
            } catch (err) {
                setErrMsg("Failed to load car details");
            }
        };

        fetchCar();
    }, [id, getCarApi]);

    //update
    const handleUpdate = async (e) => {
        e.preventDefault();

        setSuccessMsg("");
        setErrMsg("");

        const payload = {
            carNumber,
            brand,
            model,
            pricePerDay,
            location,
            color,
            fuelType,
            seats
        };

        try {
            const config = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            };

            await axios.put(updateApi, payload, config);
            setSuccessMsg("Car updated successfully");

            setTimeout(() => {
                navigate("/owner/my-cars");
            }, 1500);
        } catch (err) {
            setErrMsg(err.response?.data?.message || "Update failed");
        }
    };


    return (
        <div className="add-car-page">
            <OwnerNavbar />
            <div className="add-car-container">
                <button className="back-btn" onClick={() => navigate('/owner/my-cars')}>
                    &larr; Back to My Cars
                </button>

                <div className="add-car-card">
                    <div className="add-car-header">
                        <i className="bi bi-pencil-square header-icon"></i>
                        <h2>Update Car</h2>
                        <p>Modify the details of your listed car</p>
                    </div>

                    {errMsg && (
                        <div className="form-alert error">
                            <i className="bi bi-exclamation-circle-fill"></i> {errMsg}
                        </div>
                    )}
                    {successMsg && (
                        <div className="form-alert success">
                            <i className="bi bi-check-circle-fill"></i> {successMsg}
                        </div>
                    )}

                    <form className="add-car-form" onSubmit={handleUpdate}>
                        <div className="form-row">
                            <div className="form-field">
                                <label><i className="bi bi-hash"></i> Car Number</label>
                                <input
                                    type="text"
                                    placeholder="e.g. MH01AB1234"
                                    value={carNumber}
                                    readOnly
                                    className="disabled-input"
                                />
                            </div>
                            <div className="form-field">
                                <label><i className="bi bi-geo-alt-fill"></i> Location</label>
                                <input
                                    type="text"
                                    placeholder="e.g. Hyderabad"
                                    value={location}
                                    onChange={(e) => setLocation(e.target.value)}
                                />
                            </div>
                        </div>

                        <div className="form-row">
                            <div className="form-field">
                                <label><i className="bi bi-building"></i> Brand</label>
                                <input
                                    type="text"
                                    placeholder="e.g. Maruti Suzuki"
                                    value={brand}
                                    onChange={(e) => setBrand(e.target.value)}
                                />
                            </div>
                            <div className="form-field">
                                <label><i className="bi bi-car-front"></i> Model</label>
                                <input
                                    type="text"
                                    placeholder="e.g. Swift"
                                    value={model}
                                    onChange={(e) => setModel(e.target.value)}
                                />
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
                            </div>
                            <div className="form-field">
                                <label><i className="bi bi-palette-fill"></i> Color</label>
                                <input
                                    type="text"
                                    placeholder="e.g. White"
                                    value={color}
                                    onChange={(e) => setColor(e.target.value)}
                                />
                            </div>
                        </div>

                        <div className="form-row">
                            <div className="form-field">
                                <label><i className="bi bi-fuel-pump-fill"></i> Fuel Type</label>
                                <select value={fuelType} onChange={(e) => setFuelType(e.target.value)}>
                                    <option value="">Select Fuel Type</option>
                                    <option value="PETROL">PETROL</option>
                                    <option value="DIESEL">DIESEL</option>
                                    <option value="ELECTRIC">ELECTRIC</option>
                                    <option value="CNG">CNG</option>
                                </select>
                            </div>
                        </div>

                        <div className="form-row">
                            <div className="form-field">
                                <label><i className="bi bi-people-fill"></i> Seats</label>
                                <input
                                    type="number"
                                    placeholder="e.g. 5"
                                    value={seats}
                                    onChange={(e) => setSeats(e.target.value)}
                                />
                            </div>
                        </div>

                        <button type="submit" className="submit-car-btn">
                            <span><i className="bi bi-check-circle"></i> Update Car</span>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default EditCar;