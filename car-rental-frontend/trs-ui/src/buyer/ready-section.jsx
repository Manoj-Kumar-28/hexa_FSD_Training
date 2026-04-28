
import { useNavigate } from 'react-router-dom';

function ReadySection() {
    const navigate = useNavigate();

    const handleBookNow = () => {
        if (localStorage.getItem('token')) {
            navigate('/browse');
        } else {
            navigate('/login');
        }
    };

    return (
        <div className="ready-section">
            <div className="ready-content">
                <div className="ready-text">
                    <h2 className="ready-title">Ready to start your<br />journey with RoadReady?</h2>
                    <p className="ready-subtitle">Join thousands of satisfied travelers today and experience premium car rental like never before.</p>
                </div>
                <div className="ready-actions">
                    <button className="ready-btn btn-primary-custom" onClick={handleBookNow}>Book a Car Now</button>
                    <button className="ready-btn btn-primary-custom" onClick={() => navigate('/owner-signup')}>Become a Partner</button>
                </div>
            </div>
        </div>
    );
}

export default ReadySection;
