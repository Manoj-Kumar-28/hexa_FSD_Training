import { useEffect, useState } from "react"
import { Link, useNavigate } from "react-router-dom"
import axios from "axios"

function Navbar() {

    const navigate = useNavigate()
    const [userName, setUserName] = useState("")

    useEffect(() => {
        const fetchUser = async () => {
            const token = localStorage.getItem('token')
            if (token) {
                try {
                    const config = {
                        headers: {
                            "Authorization": "Bearer " + token
                        }
                    }
                    const response = await axios.get("http://localhost:8080/api/owner/get-one", config)
                    setUserName(response.data.name)
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
        <div className="top_menu" style={{ width: '100%', marginBottom: '2rem' }}>
            <nav className="navbar navbar-expand-lg custom-navbar">
                <div className="container-fluid">
                    <Link className="navbar-brand" to='/owner-dashboard'>
                        🚗 <span>RoadReady</span> <small className="text-muted ms-2" style={{ fontSize: '0.7rem', opacity: 0.6 }}>Owner Panel</small>
                    </Link>

                    <div className="collapse navbar-collapse">
                        <ul className="navbar-nav me-auto">
                        </ul>

                        <div className="d-flex align-items-center gap-3">
                            {localStorage.getItem('token') && (
                                <div className="nav-user-profile">
                                    <div className="user-avatar" style={{ background: 'linear-gradient(135deg, #34d399, #60a5fa)', opacity: 0.9 }}>
                                        {userName.charAt(0).toUpperCase()}
                                    </div>
                                    <span className="user-name">Hi, {userName}</span>
                                    <div className="nav-divider"></div>
                                    <button className="nav-btn-logout-minimal" type="button" onClick={logout} title="Logout">
                                        <i className="bi bi-box-arrow-right"></i>
                                    </button>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </nav>
        </div>
    )
}
export default Navbar