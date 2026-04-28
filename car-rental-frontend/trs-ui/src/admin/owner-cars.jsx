import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import './css/admin-dashboard.css';

function AdminOwnerCars() {
    const { ownerId } = useParams();
    const [cars, setCars] = useState([]);
    const [errMsg, setErrMsg] = useState('');

    useEffect(() => {
        const fetchCars = async () => {
            try {
                const config = {
                    headers: {
                        "Authorization": 'Bearer ' + localStorage.getItem('token')
                    }
                };
                const response = await axios.get(`http://localhost:8080/api/admin/owner/cars/${ownerId}/v1`, config);
                setCars(response.data)
            } catch (err) {
                setErrMsg("Could not load cars.");
            }
        };

        if (ownerId) {
            fetchCars();
        }
    }, [ownerId]);

    return (
        <div className="mt-4">
            <div className="buyer-list-header">
                <h3 className="buyer-list-title">Cars for Owner ID: {ownerId}</h3>
            </div>

            {errMsg && (
                <div className="bookings-error">
                    <i className="bi bi-exclamation-circle-fill"></i> {errMsg}
                </div>
            )}

            {cars.length === 0 && !errMsg ? (
                <div className="no-buyers-message" style={{ marginTop: '2rem' }}>
                    <i className="bi bi-car-front"></i>
                    <h4>No Cars Found</h4>
                    <p>This owner hasn't registered any cars yet.</p>
                </div>
            ) : (
                <div className="glass-table-container">
                    <table className="premium-table">
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Brand</th>
                                <th scope="col">Model</th>
                                <th scope="col">Car Number</th>
                                <th scope="col">Location</th>
                                <th scope="col">Seats</th>
                                <th scope="col">Price/Day</th>
                                <th scope="col">Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            {cars.map((c) => (
                                <tr key={c.id}>
                                    <td>{c.id}</td>
                                    <td>{c.brand}</td>
                                    <td>{c.model}</td>
                                    <td>{c.carNumber}</td>
                                    <td>{c.location}</td>
                                    <td>{c.seats}</td>
                                    <td>₹{c.pricePerDay}</td>
                                    <td>
                                        <span className={`badge ${c.carStatus === 'AVAILABLE' ? 'bg-success' : c.carStatus === 'BOOKED' ? 'bg-primary' : 'bg-secondary'}`}>
                                            {c.carStatus}
                                        </span>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}

export default AdminOwnerCars;
