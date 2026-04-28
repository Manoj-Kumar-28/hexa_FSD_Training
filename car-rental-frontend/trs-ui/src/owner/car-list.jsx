import axios from "axios"
import { useEffect, useState } from "react"
import { useParams } from "react-router-dom"

function CarList() {
    console.log("CarList rendered")
    const getCarsApi = "http://localhost:8080/api/owner/my-cars/v2"
    const [cars, setCars] = useState([])
    const { carStatus } = useParams();

    useEffect(() => {
        const fetchTickets = async () => {
            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }
            const response = await axios.get(getCarsApi, config)
            setCars(response.data)
            filter(response.data)

        }

        const filter = (data) => {
            let filterData = data.filter(car => car.carStatus === carStatus)
            setCars([...filterData])
        }

        fetchTickets()

    }, [carStatus])

    return (
        <div className="glass-table-container">
            {cars.length === 0 ? (
                <div className="text-center py-5">
                    <i className="bi bi-info-circle text-white" style={{ fontSize: '3rem', opacity: 0.8 }}></i>
                    <h4 className="mt-3 text-white">No {carStatus.toLowerCase()} vehicles</h4>
                    <p className="text-white">There are currently no vehicles with the status: {carStatus}</p>
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
                            <th scope="col">Price/Day</th>
                            <th scope="col">Location</th>
                            <th scope="col">Seats</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            cars.map((car, index) => (
                                <tr key={index}>
                                    <td className="fw-bold text-white">{index + 1}</td>
                                    <td>{car.carNumber}</td>
                                    <td>{car.brand}</td>
                                    <td>{car.model}</td>
                                    <td>
                                        <span className={`status-badge ${car.carStatus.toLowerCase()}`}>
                                            {car.carStatus}
                                        </span>
                                    </td>
                                    <td>₹{car.pricePerDay}</td>
                                    <td>{car.location}</td>
                                    <td>{car.seats}</td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            )}
        </div>
    )
}
export default CarList