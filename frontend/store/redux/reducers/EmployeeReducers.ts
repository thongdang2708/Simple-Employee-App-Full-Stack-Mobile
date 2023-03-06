
export interface EmployeeProps {
    id: number,
    name: string,
    department_id: number,
    department_name: string,
    manager_id: number
}

interface InitialStateProps {
    paginatedEmployees: EmployeeProps[] | [],
    employees: EmployeeProps[] | [],
    isSuccess: boolean,
    isError: boolean,
    message: string
}

const initialValues : InitialStateProps = {
    paginatedEmployees: [],
    employees: [],
    isSuccess: false,
    isError: false,
    message: ""
}

export interface EmployeeAction {
    type: string,
    payload?: any
}

//Set page size variable
let pageSize = 2;


export const employeeReducer = (state : InitialStateProps = initialValues, action : EmployeeAction) => {

    switch (action.type) {
        case "GET_ALL_EMPLOYEES_PAGINATION_SUCCESS":
            return {
                ...state,
                paginatedEmployees: action.payload,
                isSuccess: true,
                isError: false,
                message: ""
            }
        case "GET_ALL_EMPLOYEES_PAGINATION_FAIL":
            return {
                ...state,
                isError: true,
                message: action.payload
            }
        case "GET_ALL_EMPLOYEES_SUCCESS":
            return {
                ...state,
                employees: action.payload,
                isSuccess: true,
                isError: false,
                message: ""
            }
        case "ADD_EMPLOYEE_SUCCESS":
            return {
                ...state,
                employees: [...state.employees, action.payload],
                paginatedEmployees: state.paginatedEmployees.length < pageSize ? [...state.paginatedEmployees as EmployeeProps[], action.payload] : state.paginatedEmployees,
                isSuccess: true,
                isError: false,
                message: ""
            }
        case "ADD_EMPLOYEE_FAIL":
            return {
                ...state,
                isError: true,
                message: action.payload
            }
        case "UPDATE_EMPLOYEE_SUCCESS":
            return {
                ...state,
                employees: state.employees.map((employee :EmployeeProps) => employee.id === action.payload.id ? action.payload : employee),
                paginatedEmployees: state.paginatedEmployees.map((employee : EmployeeProps) => employee.id === action.payload.id ? action.payload : employee),
                isSuccess: true,
                isError: false,
                message: ""
            }
        case "UPDATE_EMPLOYEE_FAIL":
                return {
                    ...state,
                    isError: true,
                    message: action.payload
            }
        case "DELETE_EMPLOYEE_SUCCESS":
                return {
                    ...state,
                    employees: state.employees.filter((employee : EmployeeProps) => employee.id !== action.payload),
                    paginatedEmployees: state.paginatedEmployees.filter((employee : EmployeeProps) => employee.id !== action.payload),
                    isSucces: true,
                    isError: false,
                    message: ""
                }
        case "DELETE_EMPLOYEE_FAIL":
                return {
                    ...state,
                    isError: true,
                    message: action.payload
            }
        case "GET_ALL_EMPLOYEES_FAIL":
            return {
                ...state,
                isError: true,
                message: action.payload
            }
        default: 
            return state
    }
}