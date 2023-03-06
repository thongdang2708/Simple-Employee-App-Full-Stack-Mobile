
import React, { useContext } from 'react';
import {View, Text, StyleSheet, Pressable} from "react-native";
import colors from '../constants/colors';
import { Department } from '../store/redux/reducers/DepartmentReducers';
import {Ionicons} from "@expo/vector-icons";
import CommonIconButton from '../ui/CommonIconButton';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { dispatchStore } from '../store/redux/store';
import { deleteDepartment } from '../store/redux/actions/DepartmentActions';
import { RootParamList } from '../App';
import AuthContext from '../store/context/AuthContext';

function SingleDeparment({id, name, createdAt, manager_id} : Department) {

    //Get auth context
    let {token} = useContext(AuthContext);

    //Handle delete single department
    const handleDelete = () => {
        console.log(id);
        dispatchStore(deleteDepartment(token as string, id) as any);
    };

    //Set navigation
    let navigation = useNavigation<StackNavigationProp<RootParamList>>();

    //Handle to update department page
    const handleToUpdate = () => {
        navigation.navigate("AddOrUpdateDepartmentScreen", {
            id: id
        });
    }

  return (
    <View>

    <View style={styles.buttonContainer}>
        
        <CommonIconButton name={"close-outline"} size={24} color={colors.red} width={50} backgroundColor={colors.lightPink} onPress={handleDelete}/>
    </View>

    <Pressable onPress={handleToUpdate}>
    <View style={styles.singleContentContainer}>
        <Text style={styles.text}> Name of Department: {name} </Text>
        <Text style={styles.text}> Department is created at: {createdAt} </Text>
    </View>
    </Pressable>
    </View>
  )
};

const styles = StyleSheet.create({
    singleContentContainer: {
        borderRadius: 4,
        padding: 10,
        borderWidth: 2,
        backgroundColor: colors.offWhite,
        marginVertical: 12
    },
    text: {
        fontWeight: "bold",
        fontSize: 15,
        marginVertical: 10
    },
    buttonContainer: {
        flexDirection: "row",
        justifyContent: "flex-end"
    }
});

export default SingleDeparment