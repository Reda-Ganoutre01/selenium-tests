  'use client'

  import { useState } from "react";


  export default function Home() {
    const [task, setTask] = useState('');
    const [todos, setTodos] = useState([]);

    
    const addTask = () => {
      // if(task.trim()!==""){
      //   setTodos([...todos, { text: task, status: 'inProgress' }]);
      //   setTask('');
      // }
      // else{
      //   alert("Champ vide!!")
      // }
      setTodos([...todos, { text: task, status: 'inProgress' }]);
        setTask('');
    };

  
    const toggleTaskStatus = (index) => {
      const newTodos = [...todos];
      newTodos[index].status = newTodos[index].status === 'inProgress' ? 'completed' : 'inProgress';
      setTodos(newTodos);
    };

    
    const deleteTask = (index) => {
      
      // const newTodos = todos.filter((_, i) => i !== index);
      // setTodos(newTodos);

      
    };

    return (
      <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100 py-10">
        <h1 className="text-4xl font-bold mb-6">To-Do App</h1>
        <div className="flex items-center mb-4">
          <input
            type="text"
            className="p-2 rounded-l-md border-2 border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-400"
            placeholder="Ajouter une tâche"
            value={task}
            onChange={(e) => setTask(e.target.value)}
          />
          <button
            className="p-2 bg-blue-500 text-white rounded-r-md hover:bg-blue-600 transition duration-300"
            onClick={addTask}
          >
            Ajouter
          </button>
        </div>

        <ul className="w-full max-w-md space-y-4">
          {todos.map((todo, index) => (
            <li
              key={index}
              className={`flex justify-between items-center p-4 rounded-lg shadow-md ${todo.status === 'completed' ? 'bg-green-200' : 'bg-yellow-200'}`}
            >
              <span
                className={`text-lg font-semibold ${todo.status === 'completed' ? 'line-through text-gray-500' : ''}`}
              >
                {todo.text || " "} {}
              </span>
              <div className="space-x-2">
                <button
                  className={`px-3 py-1 text-white rounded-md ${todo.status === 'completed' ? 'bg-gray-500 hover:bg-gray-600' : 'bg-green-500 hover:bg-green-600'}`}
                  onClick={() => toggleTaskStatus(index)}
                >
                  {todo.status === 'completed' ? 'Reactiver' : 'Completer'}
                </button>
                <button
                  className="px-3 py-1 text-white bg-red-500 rounded-md hover:bg-red-600"
                  onClick={() => deleteTask(index)}
                >
                  Supprimer
                </button>
              </div>
            </li>
          ))}
        </ul>
      </div>
    );
  }
