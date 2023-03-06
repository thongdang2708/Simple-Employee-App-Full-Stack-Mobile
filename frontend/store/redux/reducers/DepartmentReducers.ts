
export interface Department {
    id: number,
    name: string,
    createdAt: string,
    manager_id?: number
}

interface InitialValueProps {
    departments: Department[] | [],
    department: Department | {},
    isSuccess: false,
    isError: false,
    message: ""
}

const initialValues : InitialValueProps = {
    departments: [],
    department: {},
    isSuccess: false,
    isError: false,
    message: ""
}

export interface DepartmentAction {
    type: string,
    payload?: any
}

export const departmentReducer = (state : InitialValueProps = initialValues, action: DepartmentAction) => {

    switch (action.type) {
        case "GET_ALL_DEPARTMENTS_SUCCESS":
            return {
                ...state,
                departments: action.payload,
                isSuccess: true,
                isError: false,
                message: ""
            }
        case "GET_ALL_DEPARTMENTS_FAIL":
            return {
                ...state,
                isError: true,
                message: action.payload
            }
        case "GET_SINGLE_DEPARTMENT_SUCCESS":
            return {
                ...state,
                department: action.payload,
                isSuccess: true,
                isError: false,
                message: ""
            }
        case "GET_SINGLE_DEPARTMENT_FAIL":
            return {
                ...state,
                isError: true,
                message: action.payload
            }
        case "ADD_DEPARTMENT_SUCCESS":
            return {
                ...state,
                departments: [...state.departments as Department[], action.payload],
                isSuccess: true,
                isError: false,
                message: ""
            }
        case "ADD_DEPARTMENT_FAIL":
               return {
                ...state,
                isError: true,
                message: action.payload
        }
        case "DELETE_DEPARTMENT_SUCCESS":
                return {
                    ...state,
                    departments: state.departments.filter((department : Department) => department.id !== action.payload),
                    isSuccess: true,
                    isError: false,
                    message: ""
                }
        case "DELETE_DEPARTMENT_FAIL":
            return {
                ...state,
                isError: true,
                message: action.payload
            }
        case "UPDATE_DEPARTMENT_SUCCESS":
            return {
                ...state,
                departments: state.departments.map((department : Department) => department.id === action.payload.id ? action.payload : department),
                isSuccess: true,
                isError: false,
                message: ""
            }
        case "UPDATE_DEPARTMENT_FAIL":
            return {
                ...state,
                isError: true,
                message: action.payload
            }
        default: 
            return state
    }
}