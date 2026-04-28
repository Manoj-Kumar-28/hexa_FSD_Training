import { Link, useNavigate } from "react-router-dom"

function TopMenu() {

    const navigate = useNavigate()
    const logout = () => {
        localStorage.clear()
        navigate("/login")
    }

    return (
        <div className="top_menu">

            <div className="row">
                <div className="col-lg-12">

                    <nav className="navbar navbar-expand-lg bg-body-tertiary">
                        <div className="container-fluid">

                            <Link className="navbar-brand" to="/">🚗 RoadReady - Admin</Link>

                            <button
                                className="navbar-toggler"
                                type="button"

                            >
                                <span className="navbar-toggler-icon"></span>
                            </button>

                            <div className="collapse navbar-collapse" id="navbarSupportedContent">

                                <ul className="navbar-nav me-auto mb-2 mb-lg-0">


                                </ul>

                                <form className="d-flex">

                                    <button className="btn btn-secondary mx-4 " type="button" onClick={logout}>
                                        Logout
                                    </button>

                                </form>

                            </div>
                        </div>
                    </nav>

                </div>
            </div>

        </div>
    )
}
export default TopMenu