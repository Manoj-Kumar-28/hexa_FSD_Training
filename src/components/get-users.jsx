import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function GetUsers() {

    const [users, setUsers] = useState([]);
    const [errMsg, setErrMsg] = useState(undefined);
    const [successMsg,setSuccessMsg]=useState(undefined);

    const apiUrl = "https://jsonplaceholder.typicode.com/users";
    const deleteApi="https://jsonplaceholder.typicode.com/users/";

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(apiUrl);
                setUsers(response.data);
                setErrMsg(undefined);
            } catch (err) {
                setErrMsg(err.message);
            }
        }
        fetchData();
    }, [])

    const deleteUser=async(id)=>{
        if(!window.confirm("want to delete for sure...")) return
        try{
            await axios.delete(deleteApi+id)

            const updatedData=users.filter(user=>
                user.id !==id
            )
            setUsers(updatedData)
            setErrMsg(undefined)
            setSuccessMsg("Deleted successfully")
        }catch(err){
            setErrMsg(err.message)
        }
    }


    return (
        <div className="container">
            <div className="row mt-3">
                <div className="col-sm-2"></div>
                <div className="col-lg-8">
                    <div className="card">
                        <div className="card-header">
                            <div className="row">
                                <div className="col-sm-6">
                                <h5>All Users</h5>
                            </div>
                            <div className="col-sm-4"></div>
                            <div className="col-sm-2">
                                <Link to="/add-users"><button>Add new User</button></Link>
                            </div>
                            </div>
                        </div>
                        <div className="card-body">
                            {
                                errMsg === undefined?"":
                                <div className="alert alert-danger">
                                    {errMsg}
                                    </div>
                            }
                            {
                                successMsg === undefined?"":
                                <div className="alert alert-success">
                                    {successMsg}
                                    </div>
                            }
                            <table className="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th>Company Name</th>
                                        <th>ACTION</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        users.map((user) => (
                                            <tr key={user.id}>
                                                <td>{user.id}</td>
                                                <td>{user.name}</td>
                                                <td>{user.email}</td>
                                                <td>{user.phone}</td>
                                                <td>{user.company.name}</td>
                                                <td>
                                                    <button onClick={()=>deleteUser(user.id)}>Delete</button>
                                                </td>
                                            </tr>
                                        ))
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div className="col-sm-2"></div>
            </div>
        </div>
    )
}
export default GetUsers;