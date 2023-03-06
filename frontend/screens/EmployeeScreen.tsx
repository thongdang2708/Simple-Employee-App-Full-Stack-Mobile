
import React, { useContext, useLayoutEffect, useState } from 'react';
import {View, Text, ListRenderItem} from "react-native";
import { useEffect } from 'react';
import { getAllEmployees, getEmployeesWithPagination } from '../store/redux/actions/EmployeeActions';
import { dispatchStore, RootState } from '../store/redux/store';
import CommonIconButton from '../ui/CommonIconButton';
import AuthContext from '../store/context/AuthContext';
import { StyleSheet } from 'react-native';
import colors from '../constants/colors';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootParamList } from '../App';
import { useSelector } from 'react-redux';
import InputForUpdating from '../components/InputForUpdating';
import { FlatList } from 'react-native';
import { TextInput } from 'react-native';
import { EmployeeProps } from '../store/redux/reducers/EmployeeReducers';
import SingleEmployee from '../components/SingleEmployee';


function EmployeeScreen() {

    //Get auth context
    let {token} = useContext(AuthContext);

    //Set navigation
    let navigation = useNavigation<StackNavigationProp<RootParamList>>();

    //Get employee state from redux
    let {paginatedEmployees, employees} = useSelector((state : RootState) => state.employee);
    
    //Set state for current page and page size

    let [offset, setOffset] = useState(1);

    const pageSize = 2;
   
    //Total page 
    let totalPage = Math.ceil(employees.length / pageSize);

    //Set effect when there are no items in a certain page
    useEffect(() => {

        if (paginatedEmployees.length == 0 && offset > 1) {
            setOffset((value) => value - 1);
        }

    }, [paginatedEmployees]);

    //Set effect to set header for employee page

   
    useLayoutEffect(() => {
        const handleToAddOrUpdatePage = () => {
            navigation.navigate("AddOrUpdateEmployeeScreen", {});
        }

        navigation.setOptions({
            headerRight: ({tintColor}) => {
                return <CommonIconButton name={"add-outline"} color={tintColor as string} size={20} width={50} backgroundColor={colors.grey} onPress={handleToAddOrUpdatePage}/>
            },
            
        })  
    },[navigation]);

    //Set effect to fetch employees with pagination

    useEffect(() => {
        dispatchStore(getEmployeesWithPagination(token as string, offset-1, pageSize) as any);
    },[offset, pageSize]);

    //Set effect to fetch all employees

    useEffect(() => {
        dispatchStore(getAllEmployees(token as string) as any);
    },[]);

    //Function to handle go back to previous page

    const handleBack = () => {
        navigation.navigate("UserScreen");
    }

    

    //Handle convertion from string to number
    const convertString = (value : number) => {

        let offsetString = "";
        offsetString += value;

        return offsetString;
    }

    //Function to handle change for input
    const handleChange = (text : string) => {
        let offsetNumber = parseInt(text);

        if (offsetNumber <= 1) {
            setOffset(1);
        } else if (offsetNumber >= totalPage && totalPage !== 0) {
            setOffset(totalPage);
        } else {
            setOffset(offsetNumber);
        }
       
    };



    //Function to decrease offset
    const handleBackNumber = () => {
        offset--;

        if (offset <= 1) {
            setOffset(1);
        }
    }

    //Function to increase offset

    const handleMoveNumber = () => {
        offset++;

        if (offset >= totalPage && totalPage !== 0) {
            setOffset(totalPage);
        }
    }


    //Handle render single employee
    const handleRenderItem : ListRenderItem<EmployeeProps>= ({item, index}) => {

        return <SingleEmployee {...item}/>
    };

    console.log(token);
    


    if (employees.length === 0) {

        return (
            <View style={styles.screen}>
            <CommonIconButton name={"arrow-back-outline"} color={"black"} size={20} width={50} backgroundColor={colors.grey} onPress={handleBack}/>
           <Text style={styles.title}> Employee List </Text>
   
            <Text style={styles.errorText}> There are no employees! </Text>
       </View>
        )
    }
    

  return (
    <View style={styles.screen}>
         <CommonIconButton name={"arrow-back-outline"} color={"black"} size={20} width={50} backgroundColor={colors.grey} onPress={handleBack}/>
        <Text style={styles.title}> Employee List </Text>


        <View style={styles.list}>
                <FlatList data={paginatedEmployees} renderItem={handleRenderItem} keyExtractor={(item : any, index : any) => {
                    return item.id
                }}/>
        </View>

        <View style={styles.buttonsContainer}>
            

            <CommonIconButton name={"caret-back-outline"} color={"black"} size={20} width={50} backgroundColor={colors.neon} onPress={handleBackNumber}/>
            
            <View style={styles.inputContainer}>
            <TextInput value={convertString(offset)} keyboardType="number-pad" onChangeText={(text : string) => handleChange(text)}/>
            </View>

            <CommonIconButton name={"caret-forward-outline"} color={"black"} size={20} width={50} backgroundColor={colors.neon} onPress={handleMoveNumber}/>
        </View>
    </View>
  )
}

const styles = StyleSheet.create({
    screen: {
        flex: 1,
        padding: 10,
        backgroundColor: colors.skinColor
    },
    title: {
        fontWeight: "bold",
        fontSize: 24,
        textAlign: "center"
    },
    errorText: {
        textAlign: "center",
        color: colors.red,
        marginVertical: 40,
        fontSize: 20,
        fontWeight: "bold"
    },
    inputContainer: {
        flexDirection: "row",
        paddingVertical: 10,
        paddingHorizontal: 15,
        marginHorizontal: 15,
        backgroundColor: colors.grey,
        borderRadius: 5,
        borderWidth: 2
        
    },
    buttonsContainer: {
        flexDirection: "row",
        alignItems: "center",
        justifyContent: "center",
        marginVertical: 20
    },
    list: {
        marginVertical: 15,
        height: "40%"
    }
});

export default EmployeeScreen