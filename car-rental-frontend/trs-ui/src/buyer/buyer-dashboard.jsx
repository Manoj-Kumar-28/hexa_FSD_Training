import { useEffect, useState } from "react"
import BuyerNavbar from "./buyerNavbar"
import { useNavigate } from "react-router-dom"
import axios from "axios"
import "./css/buyer-dashboard.css"
import FeaturedVehicles from "./featured-vehicles"
import HeroSection from "./hero-section"
import JourneySection from "./journey-section"
import ReadySection from "./ready-section"
import BuyerFooter from "./buyer-footer"

function BuyerDashboard() {

    const [buyer, setBuyer] = useState(undefined)

    const navigate = useNavigate()
    const getOneApi = "http://localhost:8080/api/buyer/get-one"
    useEffect(() => {
        const fetchOwner = async () => {
            const config = {
                headers: {
                    "Authorization": 'Bearer ' + localStorage.getItem('token')
                }
            }
            try {
                const response = await axios.get(getOneApi, config)
                setBuyer(response.data)
            } catch (err) {
                navigate("/login")
            }
        }
        fetchOwner()
    }, [])

    return (
        <div className="dashboard-container">

            <BuyerNavbar />

            {buyer && (
                <div className="container mt-4">
                    <div className="welcome-banner">
                        <h2 className="welcome-text">
                            Welcome back, <span>{buyer.name}</span>!
                        </h2>
                        <p className="welcome-subtext">Ready to find your perfect ride today?</p>
                    </div>
                </div>
            )}

            {/* Hero Section */}
            <HeroSection />

            {/* Featured Vehicles */}
            <FeaturedVehicles />

            {/* Journey Section */}
            <JourneySection />

            {/* Ready Section */}
            <ReadySection />

            {/* Footer */}
            <BuyerFooter />

        </div>
    )
}
export default BuyerDashboard