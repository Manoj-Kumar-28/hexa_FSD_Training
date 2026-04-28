import axios from "axios"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import Navbar from "./nav-bar"
import Sidebar from "./side-bar"
import './css/owner-dashboard.css'

function MyCars() {
    console.log("CarList rendered")
    const getCarsApi = "http://localhost:8080/api/owner/my-cars/v2"
    const [cars, setCars] = useState([])
    const [successMsg, setSuccessMsg] = useState(undefined)
    const [errMsg, setErrMsg] = useState(undefined)
    const navigate = useNavigate()

    useEffect(() => {
        const fetchTickets = async () => {
            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }
            try {
                const response = await axios.get(getCarsApi, config)
                setCars(response.data)
                setErrMsg(undefined)
            } catch (err) {
                setErrMsg("Failed to fetch cars")
            }

        }

        fetchTickets()
    }, [])

    const updateCar = (id) => {
        navigate(`/owner/edit-car/${id}`)
    }

    const toggleInactive = async (id) => {
        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        }
        try {
            //activate to be added
            const response = await axios.put(`http://localhost:8080/api/owner/cars/${id}/inactive`, {}, config)

            setCars(cars.map(car => car.id === id ? { ...car, carStatus: response.data.carStatus } : car))
            setErrMsg(undefined)
        } catch (err) {
            setErrMsg("Failed to update car status")
        }
    }

    const deleteCar = async (id) => {
        if (!window.confirm("Are you sure you want to delete this car?")) return;
        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        }
        try {
            await axios.delete(`http://localhost:8080/api/owner/delete/${id}`, config)
            setSuccessMsg(`Car Deleted Successfully`)
            setCars(cars => cars.filter(car => car.id !== id))
            setErrMsg(undefined)
        } catch (err) {
            setErrMsg("Failed to delete car,Car has active bookings")
        }
    }

    return (
        <div className="owner-dashboard-container">
            <div className="container-fluid p-0">
                <Navbar />

                <div className="d-flex">
                    <Sidebar />

                    <div className="main-content flex-grow-1">
                        <div className="section-header mb-4">
                            <h2 className="section-title">My Vehicles</h2>
                        </div>
                        {errMsg && (
                            <div className="alert alert-danger bg-danger bg-opacity-10 border-danger text-danger border-0 mb-4">
                                <i className="bi bi-exclamation-circle-fill me-2"></i> {errMsg}
                            </div>
                        )}

                        {successMsg && (
                            <div className="alert alert-success bg-success bg-opacity-10 border-success text-success border-0 mb-4">
                                <i className="bi bi-check-circle-fill me-2"></i> {successMsg}
                            </div>
                        )}

                        <div className="glass-table-container mt-0">
                            {cars.length === 0 ? (
                                <div className="text-center py-5">
                                    <i className="bi bi-car-front text-white" style={{ fontSize: '3rem', opacity: 0.8 }}></i>
                                    <h4 className="mt-3 text-white">No cars found</h4>
                                    <p className="text-white">You haven't added any vehicles yet. Click "Add New Vehicle" to get started.</p>
                                </div>
                            ) : (
                                <table className="premium-table">
                                    <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Car Number</th>
                                            <th scope="col">Brand</th>
                                            <th scope="col">Model</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {
                                            cars.map((car, index) => (
                                                <tr key={index}>
                                                    <td className="text-white opacity-50">{index + 1}</td>
                                                    <td className="fw-bold">{car.carNumber}</td>
                                                    <td>{car.brand}</td>
                                                    <td>{car.model}</td>
                                                    <td>
                                                        <span className={`status-badge ${(car.carStatus || "UNKNOWN").toLowerCase()}`}>
                                                            {car.carStatus || "UNKNOWN"}
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <div className="d-flex gap-2">
                                                            <button
                                                                className="btn btn-sm btn-outline-light border-opacity-10 px-3"
                                                                onClick={() => updateCar(car.id)}
                                                            >
                                                                Update
                                                            </button>
                                                            <button
                                                                className="btn btn-sm btn-outline-warning border-opacity-10 px-3"
                                                                onClick={() => toggleInactive(car.id, car.carStatus)}
                                                                disabled={car.carStatus === 'INACTIVE'}
                                                            >
                                                                Inactive
                                                            </button>
                                                            <button
                                                                className="btn btn-sm btn-outline-danger border-opacity-10 px-3"
                                                                onClick={() => deleteCar(car.id)}
                                                            >
                                                                Delete
                                                            </button>
                                                        </div>
                                                    </td>
                                                </tr>
                                            ))
                                        }
                                    </tbody>
                                </table>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </div>



    )
}
export default MyCars