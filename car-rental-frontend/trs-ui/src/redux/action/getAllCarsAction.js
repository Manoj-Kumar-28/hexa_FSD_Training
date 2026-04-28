import axios from "axios"

export const GET_ALL_CARS="GET_ALL_CARS"

export const getAllCars = (page, size) => {
    //dispatch is defined in component
    return async (dispatch) => {
        const response = await axios.get(`http://localhost:8080/api/admin/cars?page=${page}&size=${size}`, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem("token")
            }
        })
        dispatch({
            type: GET_ALL_CARS,
            payload: response.data
        })
    }
}