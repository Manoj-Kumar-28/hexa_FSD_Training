import axios from "axios"

export const GET_ALL_BUYERS="GET_ALL_BUYERS"

export const getAllBuyers=(page,size)=>{
    //dispatch is defined in component
    return async (dispatch)=>{
        const response=await axios.get(`http://localhost:8080/api/admin/buyers?page=${page}&size=${size}`,{
            headers:{
                'Authorization':'Bearer '+localStorage.getItem("token")
            }
        })
        dispatch({
            type: GET_ALL_BUYERS,
            payload: response.data
        })
    }
}