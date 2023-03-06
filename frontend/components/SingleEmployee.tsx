
import React from 'react';
import {View, Text, StyleSheet} from "react-native";
import colors from '../constants/colors';
import { EmployeeProps } from '../store/redux/reducers/EmployeeReducers';
import { useNavigation } from '@react-navigation/native';
import { Pressable } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootParamList } from '../App';

function SingleEmployee({id, name, department_id, department_name, manager_id} : EmployeeProps) {

    //Set navigation
    let navigation = useNavigation<StackNavigationProp<RootParamList>>();

    //Set function to handle move to update page
    const handleMoveToUpdatePage = () => {
        navigation.navigate("AddOrUpdateEmployeeScreen", {
            id: id
        });
    }

  return (
    <Pressable onPress={handleMoveToUpdatePage}>
    <View style={styles.singleContainer}>
        <Text style={styles.text}> Name of employee: {name} </Text>
        <Text style={styles.text}> Work at department: {department_name} </Text>
    </View>
    </Pressable>
  )
};

const styles = StyleSheet.create({
    singleContainer: {
        backgroundColor: colors.grey,
        padding: 10,
        marginVertical: 20,
        borderRadius: 4,
        borderWidth: 2
    },
    text: {
        marginVertical: 10,
        fontWeight: "bold"
    }
});

export default SingleEmployee