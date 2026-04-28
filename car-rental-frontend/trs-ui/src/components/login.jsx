import axios from "axios"
import { useState } from "react"
import { Link, useNavigate } from "react-router-dom"
import Navbar from "../owner/nav-bar"
import "./login.css"

const Login = () => {

    const [username, setUsername] = useState(undefined)
    const [password, setPassword] = useState(undefined)
    const [token, setToken] = useState(undefined)
    const [errorMsg, setErrorMsg] = useState(undefined)

    const navigate = useNavigate();

    const loginApi = "http://localhost:8080/api/auth/login"
    const detailsApi = "http://localhost:8080/api/auth/user-details"

    const getLogin = async (e) => {
        e.preventDefault();
        //console.log(username)
        //console.log(password)

        //Generate encoded String from username and Password using btoa(binary to ASCII)
        //beacause it doesnot goes directly as basic auth first converts into Authentication header
        //can see in postman headers
        try {

            let encodedString = window.btoa(username + ":" + password)
            //console.log(encodedString)
            //we got the encoded string now push it as an header now

            //generate Authorization header
            const config = {
                headers: {
                    "Authorization": 'Basic ' + encodedString
                }
            };

            //call token api to get the token
            const response = await axios.get(loginApi, config)
            setToken(response.data.token)
            //console.log(response.data)
            //console.log(response.data.token)
            localStorage.setItem("token", response.data.token)

            //call user details API
            const detailsHeader = {
                headers: {
                    "Authorization": 'Bearer ' + response.data.token
                }
            };
            const apiResponse = await axios.get(detailsApi, detailsHeader)

            console.log(apiResponse.data.role)
            switch (apiResponse.data.role) {
                case "ADMIN":
                    navigate("/admin-dashboard")
                    break;
                case "BUYER":
                    navigate("/buyer-dashboard")
                    break;
                case "OWNER":
                    navigate("/owner-dashboard")
                    break;

            }
        } catch (err) {
            setErrorMsg("Enter correct details.")
        }
    }

    return (
        <div className="login-container">
            <div className="login-card">
                <div className="login-header">
                    <div className="login-logo">RoadReady</div>
                    <div className="login-subtitle">Welcome back! Please enter your details.</div>
                </div>

                <form onSubmit={(e) => getLogin(e)}>
                    {
                        errorMsg !== undefined && (
                            <div className="error-alert">
                                <i className="bi bi-exclamation-circle-fill"></i> {errorMsg}
                            </div>
                        )
                    }

                    <div className="form-group">
                        <label>Username</label>
                        <input type="text" className="custom-input" placeholder="Enter your username" required="required"
                            onChange={(e) => setUsername(e.target.value)} />
                    </div>

                    <div className="form-group">
                        <label>Password</label>
                        <input type="password" className="custom-input" placeholder="••••••••" required="required"
                            onChange={(e) => setPassword(e.target.value)} />
                    </div>

                    <button type="submit" className="login-btn">Sign In</button>

                    <div className="login-footer">
                        Don't have an account?
                        <Link to="/buyer/sign-up" className="login-link">Sign up</Link>
                    </div>
                </form>
            </div>
        </div>
    )
}
export default Login