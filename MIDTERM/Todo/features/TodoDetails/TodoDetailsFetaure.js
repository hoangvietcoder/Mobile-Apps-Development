import { Text, View, TouchableOpacity, TextInput  } from "react-native"
import { StyleSheet } from "react-native"
import { Colors } from "../../constants/Colors"
import { Checkbox } from "./Checkbox"
import React, { useState, useEffect, useRef } from "react"


export const TodoDetailsFetaure = ({text, isChecked, onCheck, onChangeText, onDelete,...props }) => {
  const [isEditMode, setEditMode] = useState(false)

  useEffect(() => {
    if(text === '') {
      setEditMode(true)
    }
  }, [text])

  return ( 
      <View style={styles.container}>
        <View style={{flexDirection: 'row', flex: 1, alignItems: 'center', justifyContent: 'flex-start'}}>
          <Checkbox isChecked={isChecked} onChecked={onCheck}/>
          <TouchableOpacity style={{flex: 1}} onPress={() => setEditMode(true)} disabled={isChecked }>
            {isEditMode ? 
              <TextInput
                autoFocus={true}
                // value={text}
                defaultValue={text}
                // onChangeText={onChangeText}
                placeholder={'Add new item here...'}
                onSubmitEditing={onChangeText}
                blurOnSubmit={true}
                maxLength={30}
                style={styles.input}
                onBlur={() => setEditMode(false)}
              />
            :
            <Text style={[styles.text, {
              color: isChecked ? Colors.lightGray : Colors.black,
              textDecorationLine: isChecked ?  'line-through' : 'none'
            }]} >{text}</Text>
            }
          </TouchableOpacity>
          <TouchableOpacity onPress={onDelete}>
            <Text style={styles.icon}>X</Text>
          </TouchableOpacity>
        </View>
      </View> 
  ) 
}

const styles = StyleSheet.create({
  container: {
      flex: 1,
      flexDirection: "row",
      justifyContent: "space-between",
      alignItems: "center",
      padding: 10,
  },
  icon: {
      padding: 5,
      fontSize: 25,
      color: Colors.red
  },
  input: {
      color: Colors.black,
      borderBottomColor: Colors.lightGray,
      borderBottomWidth: 0.5,
      marginHorizontal: 5,
      padding: 3,
      height: 25,
      fontSize: 18,
  },
  text: {
      padding: 3,
      fontSize: 18
  },
});