import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";

function AddUser() {

    const [name, setName] = useState(undefined)
    const [email, setEmail] = useState(undefined)
    const [phone, setPhone] = useState(undefined)
    const [companyName, setCompanyName] = useState(undefined)
    const [errMsg, setErrMsg] = useState(undefined)
    const [successMsg, setSuccessMsg] = useState(undefined)

    const postApi = "https://jsonplaceholder.typicode.com/users";

    const processUser = async (e) => {
        e.preventDefault()

        try {
            await axios.post(postApi, {
                "Name": name,
                "Email": email,
                "Phone": phone,
                "Company Name": companyName
            })
            setErrMsg(undefined)
            setSuccessMsg("User added successfully")
        } catch (err) {
            setErrMsg(err.message)
        }
    }

    return (
        <div className="container">
            <div className="row mt-3">
                <div className="col-sm-4"></div>
                <div className="col-sm-4">

                    <div className="card">
                        <div className="card-header">
                            Add User
                        </div>
                        <div className="card-body">
                            <form onSubmit={processUser}>
                                {
                                    errMsg === undefined ? "" :
                                        <div className="alert alert-danger">
                                            {errMsg}
                                        </div>
                                }
                                {
                                    successMsg === undefined ? "" :
                                        <div className="alert alert-success">
                                            {successMsg}
                                        </div>
                                }
                                <div className="mt-3">
                                    <label>Name:</label>
                                    <input type="text" className="form-control" required="required"
                                        onChange={(e) => setName(e.target.value)} />
                                </div>
                                <div className="mt-3">
                                    <label>Email:</label>
                                    <input type="email" className="form-control" required="required"
                                        onChange={(e) => setEmail(e.target.value)} />
                                </div>
                                <div className="mt-3">
                                    <label>Phone:</label>
                                    <input type="text" className="form-control" required="required"
                                        onChange={(e) => setPhone(e.target.value)} />
                                </div>
                                <div className="mt-3">
                                    <label>Company Name:</label>
                                    <input type="text" className="form-control" required="required"
                                        onChange={(e) => setCompanyName(e.target.value)} />
                                </div>
                                <div className="mt-3 text-center">
                                    <input type="submit" className="btn btn-primary" value="add post" />
                                </div>
                                <div className="mt-3">
                                    <Link to="/get-users"><p> Back to Users</p></Link>
                                </div>
                            </form>
                        </div>
                    </div>

                </div>
                <div className="col-sm-4"></div>
            </div>
        </div>
    )

}
export default AddUser;