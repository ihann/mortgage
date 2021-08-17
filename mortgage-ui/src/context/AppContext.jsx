import React, {createContext, useState} from "react"



export const AppContext = createContext();

export const AppProvider = props => {
  // const [selectedStatement, setSelectedStatement] = useState({});
  // const [messageData, setMessageData] = useState({isOpen: false, message: ""});
  const handleMessageDialog = (isOpen, message) => {
    setMessageData({
      isOpen: isOpen,
      message: message
    })
  }

  return (
    <AppContext.Provider value={[handleMessageDialog]}>
      {props.children}
    </AppContext.Provider>
  );
}
