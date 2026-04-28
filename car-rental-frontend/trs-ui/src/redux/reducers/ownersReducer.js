import { GET_ALL_OWNERS } from "../action/getAllOwnersAction"


const initialState={
    //initially owners are empty
    owners:[],
    totalPages:0,
    totalRecords:0
}

const ownerReducer=(state=initialState,action)=>{
    switch(action.type){

        //action
        case GET_ALL_OWNERS:
            return {
                ...state, //duplicating the state (Spread op)
                owners : action.payload.data,
                totalPages: action.payload.totalPages,
                totalRecords: action.payload.totalRecords
            }
        default:
            return state
    }
}
export default ownerReducer