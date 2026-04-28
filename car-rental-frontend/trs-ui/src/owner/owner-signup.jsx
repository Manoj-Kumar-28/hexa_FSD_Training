import axios from "axios"
import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import "../components/login.css"

const OwnerSignUp = () => {

    const [name, setName] = useState(undefined)
    const [email, setEmail] = useState(undefined)
    const [username, setUsername] = useState(undefined)
    const [city, setCity] = useState(undefined)
    const [phoneNumber, setPhoneNumber] = useState(undefined)
    const [password, setPassword] = useState(undefined)
    const [errorMsg, setErrorMsg] = useState(undefined)
    const [successMsg, setSuccessMsg] = useState(undefined)

    const signUpUrl = "http://localhost:8080/api/owner/sign-up"

    const processSignup = async (e) => {
        e.preventDefault();
        try {
            await axios.post(signUpUrl, {
                "name": name,
                "email": email,
                "city": city,
                "phoneNumber": phoneNumber,
                "userName": username,
                "password": password
            })

            setSuccessMsg("Sign Up successful please login")
        } catch (err) {
            setErrorMsg(err.message)
        }
    }

    return (
        <div className="login-container">
            <div className="login-card" style={{ maxWidth: '500px', padding: '2.5rem 3.5rem' }}>
                <div className="login-header">
                    <div className="login-logo">RoadReady</div>
                    <div className="login-subtitle">Create your account to get started.</div>
                </div>

                <form onSubmit={(e) => processSignup(e)}>
                    {
                        errorMsg !== undefined && (
                            <div className="error-alert">
                                <i className="bi bi-exclamation-circle-fill"></i> {errorMsg}
                            </div>
                        )
                    }
                    {
                        successMsg !== undefined && (
                            <div className="error-alert" style={{ background: 'rgba(52, 211, 153, 0.1)', border: '1px solid rgba(52, 211, 153, 0.2)', color: '#6ee7b7' }}>
                                <i className="bi bi-check-circle-fill"></i> {successMsg}
                            </div>
                        )
                    }

                    <div className="form-group" style={{ marginBottom: '1rem' }}>
                        <label>Name</label>
                        <input type="text" className="custom-input" placeholder="Enter your full name" required="required"
                            onChange={(e) => setName(e.target.value)} />
                    </div>

                    <div className="form-group" style={{ marginBottom: '1rem' }}>
                        <label>Email</label>
                        <input type="email" className="custom-input" placeholder="Enter your email" required="required"
                            onChange={(e) => setEmail(e.target.value)} />
                    </div>

                    <div className="form-group" style={{ marginBottom: '1rem' }}>
                        <label>City</label>
                        <input type="text" className="custom-input" placeholder="Enter your city" required="required"
                            onChange={(e) => setCity(e.target.value)} />
                    </div>

                    <div className="form-group" style={{ marginBottom: '1rem' }}>
                        <label>Phone Number</label>
                        <input type="tel" className="custom-input" placeholder="Enter your phone number" required="required"
                            onChange={(e) => setPhoneNumber(e.target.value)} />
                    </div>

                    <div className="form-group"  style={{ marginBottom: '1rem' }}>
                        <label>Username</label>
                        <input type="text" className="custom-input" name="random_name_123" placeholder="Choose a username" required="required" autoComplete="off"
                            onChange={(e) => setUsername(e.target.value)} />
                    </div>

                    <div className="form-group" style={{ marginBottom: '1.5rem' }}>
                        <label>Password</label>
                        <input type="password" className="custom-input" placeholder="••••••••" required="required" autoComplete="off"
                            onChange={(e) => setPassword(e.target.value)} />
                    </div>

                    <button type="submit" className="login-btn">Sign Up</button>

                    <div className="login-footer">
                        Have an account already?
                        <Link to="/login" className="login-link">Login</Link>
                    </div>
                </form>
            </div>
        </div>
    )
}
export default OwnerSignUp