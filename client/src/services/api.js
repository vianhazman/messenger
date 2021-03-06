import Axios from "axios";

const api = Axios.create({
  baseURL: "http://10.119.105.71:8080/api/",
});

const chatAPI = {
  getMessages: (groupId) => {
    console.log("Calling get messages from API");
    return api.get(`messages/${groupId}`);
  },

  sendMessage: (username, text) => {
    let msg = {
      sender: username,
      content: text,
    };
    return api.post(`send`, msg);
  },
};

export default chatAPI;
