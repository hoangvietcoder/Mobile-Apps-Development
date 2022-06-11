import { View, Text } from "react-native"
import { styles } from "./TodoDetails.style"
import { FlatList, TouchableOpacity} from "react-native"
import {useState, useEffect} from 'react'
import { Ionicons } from '@expo/vector-icons'
import { TodoDetailsFetaure } from "../../features/TodoDetails/TodoDetailsFetaure"
import { auth, db } from "../../firebase/firebase"
import { collection, deleteDoc, doc, getDoc, getDocs, setDoc } from 'firebase/firestore/lite'
import Spinner from 'react-native-loading-spinner-overlay'
import { Colors } from "../../constants/Colors"


export const TodoDetails = ({ route }) => {
  const [todoDetails, setTodDoDetails] = useState([])

  const [loading, setLoading] = useState(false)

  useEffect(() => {
    const getData = async () => {     
      setLoading(true) 
      const Data = await getDocs(collection(db, `users/${auth.currentUser?.uid}/Todos/${route.params.propKey}/TodoDetails`))
      const listTodosNew = []
      Data.forEach((doc) => {
          listTodosNew.push({
            ...doc.data(),
            key: doc.id
          })
      })
      setLoading(false) 

  
      return setTodDoDetails(listTodosNew)
    }
    
    getData()
  }, [])

  const addItem =(item) => {
    const myDoc = doc(db, `users/${auth.currentUser?.uid}/Todos/${route.params.propKey}/TodoDetails`, `${Math.random()}`)

    setDoc(myDoc, item)
    .then(() => {
      const getData = async () => {     
        setLoading(true) 
        const Data = await getDocs(collection(db, `users/${auth.currentUser?.uid}/Todos/${route.params.propKey}/TodoDetails`))
        const listTodosNew = []
        Data.forEach((doc) => {
            listTodosNew.push({
              ...doc.data(),
              key: doc.id
            })
        })
        setLoading(false) 
  
    
        return setTodDoDetails(listTodosNew)
      }
      
      getData()
    })
    .catch(err => {
      alert(err)
    })
  }

  const removeItem =(propKey) => {
    const myDoc = doc(db, `users/${auth.currentUser?.uid}/Todos/${route.params.propKey}/TodoDetails`, propKey)

    deleteDoc(myDoc)
    .then(() => {
      const getData = async () => {     
        setLoading(true) 
        const Data = await getDocs(collection(db, `users/${auth.currentUser?.uid}/Todos/${route.params.propKey}/TodoDetails`))
        const listTodosNew = []
        Data.forEach((doc) => {
            listTodosNew.push({
              ...doc.data(),
              key: doc.id
            })
        })
        setLoading(false) 
  
    
        return setTodDoDetails(listTodosNew)
      }
      
      getData()
    })
    .catch(err => {
      alert(err)
    })
  }

  const updateItem =(propKey, item) => {
    const myDoc = doc(db, `users/${auth.currentUser?.uid}/Todos/${route.params.propKey}/TodoDetails`, propKey)

    setDoc(myDoc, item, {merge: true})
    .then(() => {
      const getData = async () => {     
        setLoading(true) 
        const Data = await getDocs(collection(db, `users/${auth.currentUser?.uid}/Todos/${route.params.propKey}/TodoDetails`))
        const listTodosNew = []
        Data.forEach((doc) => {
            listTodosNew.push({
              ...doc.data(),
              key: doc.id
            })
        })
        setLoading(false) 
  
        return setTodDoDetails(listTodosNew)
      }
      
      getData()
    })
    .catch(err => {
      alert(err)
    })

  }

  return (
    <View style={styles.container}>
      {loading &&
       <Spinner
          visible={true}
          textContent={'Loading...'}
          textStyle={{color: Colors.primary}}
        />
      }
      <FlatList
        data={todoDetails}
        renderItem={({item: {text, isChecked, key}, index}) => {
          return <TodoDetailsFetaure text={text} isChecked={isChecked} 
            onCheck={() => {
              const toDoItem = todoDetails[index]
              toDoItem.isChecked = !isChecked
              updateItem(key, toDoItem)
            }}
            onChangeText={({ nativeEvent: { text }}) => {
              const toDoItem = todoDetails[index]
              toDoItem.text = text
              updateItem(key, toDoItem)
            }}
            onDelete ={() => {
              removeItem(key)
            }}
          />
        }}
      />
       <TouchableOpacity style={[styles.addIcon,  {backgroundColor: route.params.color}]} onPress={() => addItem({
        text: "",
        isChecked: false
      })}>
          <Ionicons name="ios-add" size={40} color="white"/>
      </TouchableOpacity>
    </View>
  )
}