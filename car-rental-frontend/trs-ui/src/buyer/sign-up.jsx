import axios from "axios"
import { useState } from "react"
import { Link } from "react-router-dom"
import "../components/login.css"

const BuyerSignUp = () => {

    const [name, setName] = useState(undefined)
    const [email, setEmail] = useState(undefined)
    const [username, setUsername] = useState(undefined)
    const [city, setCity] = useState(undefined)
    const [password, setPassword] = useState(undefined)
    const [errorMsg, setErrorMsg] = useState(undefined)
    const [successMsg, setSuccessMsg] = useState(undefined)

    const signUpUrl = "http://localhost:8080/api/buyer/sign-up"

    const processSignup = async (e) => {
        e.preventDefault();
        try {
            await axios.post(signUpUrl, {
                "name": name,
                "email": email,
                "city": city,
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

                    <div className="form-group mb-3" >
                        <label>Name</label>
                        <input type="text" className="custom-input" placeholder="Enter your full name" required="required"
                            onChange={(e) => setName(e.target.value)} />
                    </div>

                    <div className="form-group mb-3" >
                        <label>Email</label>
                        <input type="email" className="custom-input" placeholder="Enter your email" required="required"
                            onChange={(e) => setEmail(e.target.value)} />
                    </div>

                    <div className="form-group mb-3" >
                        <label>City</label>
                        <input type="text" className="custom-input" placeholder="Enter your city" required="required"
                            onChange={(e) => setCity(e.target.value)} />
                    </div>

                    <div className="form-group mb-3" >
                        <label>Username</label>
                        <input type="text" className="custom-input" placeholder="Choose a username" required="required"
                            onChange={(e) => setUsername(e.target.value)} />
                    </div>

                    <div className="form-group mb-3" >
                        <label>Password</label>
                        <input type="password" className="custom-input" placeholder="••••••••" required="required"
                            onChange={(e) => setPassword(e.target.value)} />
                    </div>

                    <button type="submit" className="login-btn mb-3">Sign Up</button>

                    <div className="login-footer">
                        Have an account already?
                        <Link to="/login" className="login-link">Login</Link>
                    </div>
                </form>
            </div>
        </div>
    )
}
export default BuyerSignUp