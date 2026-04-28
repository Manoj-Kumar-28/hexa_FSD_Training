import axios from "axios"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"

function AdminStats() {
    const navigate = useNavigate();

    const api = "http://localhost:8080/api/admin/stats"
    const bookingStatsApi = "http://localhost:8080/api/admin/bookingStats"
    const [statData, setStatData] = useState({
        totalCars: 0,
        totalUsers: 0,
        totalBookings: 0,
        revenue: 0,
        totalOwners: 0,
        totalBuyers: 0
    }
    )
    const [bookingStatData, setBookingStatData] = useState([])


    useEffect(() => {
        const getStats = async () => {
            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }
            const response = await axios.get(api, config)
            setStatData(response.data)
        }
        getStats()
    }, [])

    useEffect(() => {
        const getBookingStats = async () => {
            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }
            const response = await axios.get(bookingStatsApi, config)
            setBookingStatData(response.data)
        }
        getBookingStats()
    }, [])

    return (
        <div>
            <div className="row mb-4">

                <div className="col-md-3">
                    <div className="admin-stat-card">
                        <h6>Total Users</h6>
                        <h4>{statData.totalUsers}</h4>
                    </div>
                </div>

                <div className="col-md-3">
                    <div className="admin-stat-card">
                        <h6>Total Cars</h6>
                        <h4>{statData.totalCars}</h4>
                    </div>
                </div>

                <div className="col-md-3">
                    <div className="admin-stat-card">
                        <h6>Bookings</h6>
                        <h4>{statData.totalBookings}</h4>
                    </div>
                </div>

                <div className="col-md-3">
                    <div className="admin-stat-card">
                        <h6>Revenue</h6>
                        <h4>{statData.revenue}<span>rs</span></h4>
                    </div>
                </div>

            </div>

            <div className="row mb-4">
                <h4>Booking Status</h4>
                {
                    bookingStatData.map((stat, index) => (
                        <div className="col-md-3 " key={index}>
                            <div 
                                className="admin-stat-card" 
                                style={{ cursor: 'pointer' }}
                                onClick={() => navigate(`/admin-dashboard/bookings/${stat.status}`)}
                            >
                                <h6>{stat.status}</h6>
                                <h4>{stat.count}</h4>
                            </div>
                        </div>
                    ))
                }

            </div>

            <div className="row mb-4">
                <h4>Users By Role</h4>
                <div className="col-md-3">
                    <div className="admin-stat-card">
                        <h6>Owners</h6>
                        <h4>{statData.totalOwners}</h4>
                    </div>
                </div>

                <div className="col-md-3">
                    <div className="admin-stat-card">
                        <h6>Buyers</h6>
                        <h4>{statData.totalBuyers}</h4>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default AdminStats