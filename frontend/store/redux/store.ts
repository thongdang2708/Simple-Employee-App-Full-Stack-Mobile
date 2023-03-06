import { userReducer } from "./reducers/UserReducers";
import {createStore, applyMiddleware, combineReducers} from "redux";
import thunk from "redux-thunk";
import { composeWithDevTools } from "@redux-devtools/extension";
import { managerReducer } from "./reducers/ManagerReducers";
import { departmentReducer } from "./reducers/DepartmentReducers";
import { employeeReducer } from "./reducers/EmployeeReducers";
const middleware = [thunk];


const reducers = combineReducers({
    user: userReducer,
    manager: managerReducer,
    department: departmentReducer,
    employee: employeeReducer
});

export const store = createStore(reducers, composeWithDevTools(applyMiddleware(...middleware)));

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export const dispatchStore = store.dispatch;

