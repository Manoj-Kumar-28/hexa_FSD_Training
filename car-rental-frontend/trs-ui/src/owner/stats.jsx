import axios from "axios"
import { useEffect, useState } from "react"
import { Link, useNavigate, useParams } from "react-router-dom"

function Stats() {

    const navigate = useNavigate()
    const { carStatus } = useParams();

    const statsApi = "http://localhost:8080/api/owner/stats"
    const [stat, setStat] = useState({
        totalCars: 0,
        totalBookings: 0,
        totalRevenue: 0,
        available: 0,
        booked: 0,
        inactive: 0
    })

    useEffect(() => {
        const fetchStats = async () => {
            try {
                const config = {
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("token")
                    }
                }
                const response = await axios.get(statsApi, config)
                setStat(response.data)
            } catch (err) {
            }
        }
        fetchStats()
    }, [carStatus])

    return (
        <div className="stats-section">
            <div className="section-header mb-4">
                <h2 className="section-title">Fleet Overview</h2>
            </div>

            <div className="row g-3 mb-4">
                <div className="col-md-1"></div>
                <div className="col-md-3">
                    <div className="action-card stat-card-premium" onClick={() => navigate("/owner/my-cars")}>
                        <div className="stat-icon-wrapper">
                            <i className="bi bi-car-front-fill"></i>
                        </div>
                        <h5 className="mt-2">Total Vehicles</h5>
                        <div className="stat-display">
                            <span className="stat-number">{stat.totalCars}</span>
                        </div>
                    </div>
                </div>

                <div className="col-md-3">
                    <div className="action-card stat-card-premium" onClick={() => navigate("/owner/my-bookings")}>
                        <div className="stat-icon-wrapper">
                            <i className="bi bi-calendar-check"></i>
                        </div>
                        <h5 className="mt-2">Total Bookings</h5>
                        <div className="stat-display">
                            <span className="stat-number">{stat.totalBookings}</span>
                        </div>
                    </div>
                </div>

                <div className="col-md-3">
                    <div className="action-card stat-card-premium earnings">
                        <div className="stat-icon-wrapper">
                            <i className="bi bi-currency-rupee"></i>
                        </div>
                        <h5 className="mt-2">Total Earnings</h5>
                        <div className="stat-display">
                            <span className="stat-number">{stat.totalRevenue}</span>
                            <span className="stat-unit">INR</span>
                        </div>
                    </div>
                </div>
            </div>

            <div className="section-header mb-4 mt-5">
                <h2 className="section-title">Real-time Status</h2>
            </div>

            <div className="row g-4 justify-content-center">
                <div className="col-md-3">
                    <Link to={`/owner-dashboard/car-list/AVAILABLE`} className="text-decoration-none">
                        <div className="action-card status-card available">
                            <div className="status-indicator"></div>
                            <h5 className="mt-2">Available</h5>
                            <div className="stat-number-small">{stat.available}</div>
                        </div>
                    </Link>
                </div>

                <div className="col-md-3">
                    <Link to={`/owner-dashboard/car-list/BOOKED`} className="text-decoration-none">
                        <div className="action-card status-card booked">
                            <div className="status-indicator"></div>
                            <h5 className="mt-2">Booked</h5>
                            <div className="stat-number-small">{stat.booked}</div>
                        </div>
                    </Link>
                </div>

                <div className="col-md-3">
                    <Link to={`/owner-dashboard/car-list/INACTIVE`} className="text-decoration-none">
                        <div className="action-card status-card inactive">
                            <div className="status-indicator"></div>
                            <h5 className="mt-2">Inactive</h5>
                            <div className="stat-number-small">{stat.inactive}</div>
                        </div>
                    </Link>
                </div>
            </div>
        </div>
    )
}
export default Stats