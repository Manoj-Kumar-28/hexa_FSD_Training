import axios from "axios"

export const GET_ALL_OWNERS="GET_ALL_OWNERS"

export const getAllOwners=(page,size)=>{
    //dispatch is defined in component
    return async (dispatch)=>{
        const response=await axios.get(`http://localhost:8080/api/admin/owners?page=${page}&size=${size}`,{
            headers:{
                'Authorization':'Bearer '+localStorage.getItem("token")
            }
        })
        dispatch({
            type: GET_ALL_OWNERS,
            payload: response.data
        })
    }
}