import { GET_ALL_BUYERS } from "../action/getAllBuyersAction"


const initialState = {
    //intially buyers are empty
    buyers: [],
    totalPages: 0,
    totalRecords: 0
}

const buyerReducer = (state = initialState, action) => {
    switch (action.type) {

        //action
        case GET_ALL_BUYERS:
            return {
                ...state, //duplicating the state (Spread op)
                buyers: action.payload.data,
                totalPages: action.payload.totalPages,
                totalRecords: action.payload.totalRecords
            }
        default:
            return state
    }
}
export default buyerReducer