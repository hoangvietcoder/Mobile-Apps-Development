import { Text, View, TouchableOpacity, Alert  } from "react-native"
import { MaterialIcons } from '@expo/vector-icons'
import { Feather } from '@expo/vector-icons';
import { styles } from "../../screens/Home/Home.style"

export const Todo = ({ index, title, color, navigation, updateItemFromLists, removeItem, propKey}) => {

  const showConfirmDialog = (title) => {
    return Alert.alert(
      "Are your sure?",
      `Are you sure you want to remove ${title}?`,
      [
        {
          text: "No",
        },
        {
          text: "Yes",
          onPress: () => {
            removeItem(propKey)
          }
        }
      ]
    )
  }

  return ( 
    <TouchableOpacity onPress={() => {navigation.navigate('Tododetails', {title, color, propKey})}} style={[styles.itemContainer, {backgroundColor: color}]}>
      <View style={{flexDirection: 'row'}}>
        <Text style={styles.itemTitle}>
        {index+1}. 
        </Text>
        <Text style={styles.itemTitle}>
        {title}
        </Text>
      </View>
      <View style={{flexDirection: "row"}}>
        <TouchableOpacity onPress={() => {navigation.navigate('Edit Todo', {title, color, saveChanges: (item) => updateItemFromLists(item, propKey)})}}>
          <Feather name="edit" style={{marginHorizontal: 5}} size={24} color="white"/>
        </TouchableOpacity>
        <TouchableOpacity>
          <MaterialIcons name="delete-outline" style={{marginHorizontal: 5}} size={24} color="white" onPress={() => showConfirmDialog(title)}/>
        </TouchableOpacity>
      </View>
    </TouchableOpacity>
  ) 
}
