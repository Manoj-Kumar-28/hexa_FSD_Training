import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux"
import { useNavigate } from "react-router-dom";
import { getAllCars } from "../redux/action/getAllCarsAction";
import TopMenu from "./top-menu";
import AdminSidebar from "./admin-sidebar";
import './css/admin-dashboard.css';

function CarsList() {
    const [page, setPage] = useState(0);
    const size = 6;

    const dispatch = useDispatch();
    const navigate = useNavigate();
    //getting cars from store which are saved in state
    const { cars = [], totalPages = 0, totalRecords = 0 } = useSelector(state => state.Cars);

    useEffect(() => {
        //getAllCars is an action and we are calling it
        dispatch(getAllCars(page, size))
    }, [dispatch, page])


    return (
        <div className="admin-dashboard-container">
            <div className="container-fluid p-0">
                <TopMenu />

                <div className="d-flex">
                    <AdminSidebar />

                    <div className="main-content flex-grow-1 p-4">
                        <div className="buyer-list-header">
                            <h3 className="buyer-list-title">All Cars</h3>
                            <span className="buyer-count-badge">
                                {totalRecords} cars
                            </span>
                        </div>

                        {cars.length === 0 ? (
                            <div className="no-buyers-message" style={{ marginTop: '2rem' }}>
                                <i className="bi bi-car-front"></i>
                                <h4>No Cars Found</h4>
                                <p>No cars have been registered yet.</p>
                            </div>
                        ) : (
                            <>
                                <div className="cars-grid-container">
                                    {cars.map((c) => (
                                        <div key={c.id} className="car-card">
                                            <div className="car-image-section">
                                                <img
                                                    src={
                                                        c.carImage
                                                            ? `/uploads/${c.carImage}`
                                                            : "/car_card_placeholder.png"
                                                    }
                                                    alt={`${c.brand} ${c.model}`}
                                                    className="car-image"
                                                />
                                            </div>

                                            <div className="car-content">
                                                <div className="car-details">
                                                    <h7 className="car-title">{c.brand} {c.model}</h7>
                                                    <p className="car-number">
                                                        <i className="bi bi-bookmark"></i> {c.carNumber}
                                                    </p>
                                                    <p className="car-location">
                                                        <i className="bi bi-geo-alt"></i> {c.location}
                                                    </p>
                                                    <p className="car-seats">
                                                        <i className="bi bi-person"></i> {c.seats} seats
                                                    </p>
                                                </div>

                                                <div className="car-footer">
                                                    <div className="car-price-section">
                                                        <span className="car-price">₹{c.pricePerDay}</span>
                                                        <span className="car-period">/day</span>
                                                    </div>

                                                    <div className="car-actions">

                                                        <span className={`car-status-badge ${c.carStatus === 'AVAILABLE' ? 'available' : c.carStatus === 'BOOKED' ? 'booked' : 'inactive'}`}>
                                                            {c.carStatus}
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    ))}
                                </div>

                                <div className="d-flex justify-content-center mt-4">
                                    <button
                                        className="btn btn-outline-primary me-2"
                                        disabled={page === 0}
                                        onClick={() => setPage(page - 1)}
                                    >
                                        Prev
                                    </button>

                                    <span className="align-self-center text-white">
                                        Page {page + 1} of {totalPages}
                                    </span>

                                    <button
                                        className="btn btn-outline-primary ms-2"
                                        disabled={page === totalPages - 1}
                                        onClick={() => setPage(page + 1)}
                                    >
                                        Next
                                    </button>
                                </div>
                            </>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}
export default CarsList