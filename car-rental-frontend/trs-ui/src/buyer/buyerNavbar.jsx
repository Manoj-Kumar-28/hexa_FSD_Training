import { useEffect, useState } from "react"
import { Link, useNavigate } from "react-router-dom"
import axios from "axios"

function BuyerNavbar() {

    const navigate = useNavigate()
    const [userName, setUserName] = useState("")

    useEffect(() => {
        const fetchUser = async () => {
            const token = localStorage.getItem('token')
            if (token) {
                try {
                    const config = {
                        headers: {
                            "Authorization": "Bearer " + localStorage.getItem("token")
                        }
                    }
                    const response = await axios.get("http://localhost:8080/api/buyer/get-one", config)
                    setUserName(response.data.name || "User")
                } catch (err) {
                    console.error("Navbar user fetch failed")
                }
            }
        }
        fetchUser()
    }, [])

    const logout = () => {
        localStorage.clear()
        navigate("/login")
    }

    return (
        <div className="top_menu">

            <div className="row">
                <div className="col-lg-12">

                    <nav className="navbar navbar-expand-lg custom-navbar">
                        <div className="container-fluid">

                            <Link className="navbar-brand" to="/">🚗 <span>RoadReady</span></Link>

                            <div className="collapse navbar-collapse" id="navbarSupportedContent">

                                <ul className="navbar-nav me-auto mb-2 mb-lg-0">

                                    <li className="nav-item">
                                        <Link className="nav-link active" to="/buyer-dashboard">
                                            Home
                                        </Link>
                                    </li>

                                    <li className="nav-item">
                                        <Link className="nav-link" to="/browse">
                                            Cars
                                        </Link>
                                    </li>

                                    <li className="nav-item">
                                        <Link className="nav-link" to="/myBookings">
                                            Bookings
                                        </Link>
                                    </li>
                                </ul>

                                <div className="d-flex align-items-center gap-3">
                                    {localStorage.getItem('token') ? (
                                        <div className="nav-user-profile">
                                            <div
                                                className="d-flex align-items-center gap-2"
                                                style={{ cursor: 'pointer' }}
                                                onClick={() => navigate("/buyer/profile")}
                                            >
                                                <div className="user-avatar">
                                                    {userName.charAt(0).toUpperCase()}
                                                </div>
                                                <span className="user-name">Hi, {userName}</span>
                                            </div>
                                            <div className="nav-divider"></div>
                                            <button className="nav-btn-logout-minimal" type="button" onClick={logout} title="Logout">
                                                <i className="bi bi-box-arrow-right"></i>
                                            </button>
                                        </div>
                                    ) : (
                                        <button className="nav-btn-login" type="button" onClick={() => navigate("/login")}>
                                            Login
                                        </button>
                                    )}
                                </div>

                            </div>
                        </div>
                    </nav>

                </div>
            </div>

        </div>
    )
}
export default BuyerNavbar