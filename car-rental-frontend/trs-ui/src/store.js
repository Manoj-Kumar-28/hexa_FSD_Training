
import  {createStore, applyMiddleware, combineReducers } from 'redux'
import { thunk} from 'redux-thunk'
import buyerReducer from './redux/reducers/buyersReducer'
import ownerReducer from './redux/reducers/ownersReducer'
import carsReducer from './redux/reducers/carsReducer'

 const reducers = combineReducers({
    Buyer: buyerReducer,
    Owner: ownerReducer,
    Cars: carsReducer
 })

export const store=createStore(reducers,applyMiddleware(thunk))
