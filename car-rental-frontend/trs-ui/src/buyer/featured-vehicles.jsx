import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";

function FeaturedVehicles() {
    const [cars, setCars] = useState([]);
    const [errMsg, setErrMsg] = useState(undefined)
    const navigate = useNavigate();


    const getAllCarsApi = "http://localhost:8080/api/buyer/get-all";

    useEffect(() => {
        const fetchCars = async () => {
            try {
                const config = {
                    headers: {
                        "Authorization": 'Bearer ' + localStorage.getItem('token')
                    }
                };
                const response = await axios.get(getAllCarsApi, config);

                // Slicing the array to only show the top featured vehicles
                setCars(response.data.data.slice(0, 4));
            } catch (err) {
                setErrMsg("Error in fetching Featured vehicles.")
            }
        };
        fetchCars();
    }, []);

    // Alternate image if owner doesn't provide image
    const placeholderImage = "/car_card_placeholder.png";

    return (
        <div className="featured-section">
            <div className="featured-header">
                <h3 className="featured-title">Featured Cars</h3>
            </div>

            <div className="vehicle-grid">
                {cars.map((car, index) => (
                    <div className="vehicle-card" key={index}>
                        <div className="vehicle-image-container">
                            {/* Uses the placeholder image as if no image provided by the owner*/}
                            <img src={car.carImage ? `/uploads/${car.carImage}` : placeholderImage} alt={car.brand} className="vehicle-card-image" />
                        </div>
                        <div className="vehicle-info">
                            <h4 className="vehicle-name">{car.brand} {car.model}</h4>
                            <p className="vehicle-price">{car.pricePerDay} rs/day</p>
                            <button className="view-details-btn" onClick={() => navigate(`/car-details/${car.id}`)}>
                                View Details
                            </button>
                        </div>
                    </div>
                ))}

                {/* Browse All Cars Button */}
                <div className="vehicle-card browse-all-card" onClick={() => navigate('/browse')}>
                    <div className="browse-all-content">
                        <i className="bi bi-arrow-right-circle-fill browse-icon"></i>
                        <h4>Browse All Cars</h4>
                        <p>Explore our full fleet</p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default FeaturedVehicles;
