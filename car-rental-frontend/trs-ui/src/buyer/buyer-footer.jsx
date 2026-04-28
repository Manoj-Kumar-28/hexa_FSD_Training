import { Link } from 'react-router-dom';

function BuyerFooter() {
    return (
        <footer className="buyer-footer">
            <div className="footer-top">
                <div className="footer-grid">
                    <div className="footer-col brand-col">
                        <Link className="footer-brand" to="/">🚗 <span>RoadReady</span></Link>
                        <p className="footer-desc">
                            Experience premium car rental like never before. 
                            Drive the best vehicles with the best rates, guaranteed.
                        </p>
                        <div className="social-links">
                            <a href="#" className="social-icon"><i className="bi bi-instagram"></i></a>
                            <a href="#" className="social-icon"><i className="bi bi-twitter-x"></i></a>
                            <a href="#" className="social-icon"><i className="bi bi-facebook"></i></a>
                        </div>
                    </div>
                    
                    <div className="footer-col">
                        <h5 className="footer-heading">Quick Links</h5>
                        <ul className="footer-links">
                            <li><Link to="/dashboard">Home</Link></li>
                            <li><Link to="/browse">Browse Cars</Link></li>
                            <li><Link to="/myBookings">My Bookings</Link></li>
                            <li><Link to="/owner-signup">Become a Host</Link></li>
                        </ul>
                    </div>

                    <div className="footer-col">
                        <h5 className="footer-heading">Support</h5>
                        <ul className="footer-links">
                            <li><a href="#">Help Center</a></li>
                            <li><a href="#">Cancellation Options</a></li>
                            <li><a href="#">Safety Information</a></li>
                            <li><a href="#">Contact Us</a></li>
                        </ul>
                    </div>

                    <div className="footer-col">
                        <h5 className="footer-heading">Legal</h5>
                        <ul className="footer-links">
                            <li><a href="#">Terms of Service</a></li>
                            <li><a href="#">Privacy Policy</a></li>
                            <li><a href="#">Insurance Coverage</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            
            <div className="footer-bottom">
                <p>&copy; {new Date().getFullYear()} RoadReady. All rights reserved.</p>
            </div>
        </footer>
    );
}

export default BuyerFooter;
