import { GET_ALL_CARS } from "../action/getAllCarsAction"


const initialState = {
    //initially cars are empty
    cars: [],
    totalPages: 0,
    totalRecords: 0
}

const carsReducer = (state = initialState, action) => {
    switch (action.type) {

        //action
        case GET_ALL_CARS:
            return {
                ...state, //duplicating the state (Spread op)
                cars: action.payload.data,
                totalPages: action.payload.totalPages,
                totalRecords: action.payload.totalRecords
            }
        default:
            return state
    }
}
export default carsReducer