const addTaskBtn = document.querySelector(".add-task");
const overlay = document.querySelector(".overlay");

const showModel = document.querySelector(".taskform");
const closeModel = document.querySelector(".close-model");

const taskFormDescription = document.getElementById("task-description");
const taskFormDueDate = document.getElementById("task-duedate");
const taskAssignedTo = document.getElementById("assignedtask");

const saveTask = document.querySelector(".add-btn");
const taskContainer = document.querySelector(".user-assigned-tasks__all-rows");
const adminCompletedTasksContainer = document.querySelector(".user-completed-tasks__all-rows");

const updateTask = document.querySelector(".update-btn");
const myPendingTasks = document.querySelector(".my-pending-tasks__all-rows");
const myCompletedTasks = document.querySelector(".my-completed-tasks__all-rows");
const userTaskContainer = document.querySelector(".my-pending-tasks");
const adminRoleBtn = document.querySelector(".role-icon");
const userRoleBtn = document.querySelector("#user-btn");
const adminCotnainer = document.querySelector(".admin-container ");
const userContainer = document.querySelector(".user-container");
const updateUserPic = document.querySelector(".profile-pic");
const profileInput = document.getElementById("profile-input");
////////////////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////////
window.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    const roles = JSON.parse(localStorage.getItem("roles") || "[]");
    console.log(roles);
    if (!token) {
        //if user not logged in, redirect to login.html
        window.location.href = "/login.html";
        return;
    }
    if (roles.includes("ROLE_ADMIN")) {
        adminCotnainer.classList.remove("hidden");
        userContainer.classList.add("hidden");
    }


});
async function authFetch(url, options = {}) {
    const token = localStorage.getItem("token");

    options.headers = {
        ...options.headers,
        "Authorization": "Bearer " + token,

    };

    return fetch(url, options);
}
const role =   document.querySelector(".role-status");
// /////////////////////////////
async function getUsers(){
    const allUsers = await authFetch("http://localhost:8080/api/users");
    return  await allUsers.json();
}
getUsers().then(users=> {

    users.forEach(user=>{
        taskAssignedTo.options[taskAssignedTo.options.length] = new Option(`${user.firstName}`, `${user.userId}`);

    })


})

///////////////////////////////////
let currentRole = role.textContent.trim();
adminRoleBtn.addEventListener("mouseover",function(){
    if(currentRole==="User"){
        role.textContent ="Admin";
    }else if(currentRole==="Admin"){
        role.textContent ="User";
    }

})
adminRoleBtn.addEventListener("mouseout",function(){
    role.textContent = currentRole})


adminRoleBtn.addEventListener("click",function (){
    // console.log()

    if(role.textContent.trim() ==="User"){
        userContainer.classList.remove("hidden");
        adminCotnainer.classList.add("hidden")
        currentRole ="User"
    }
    if(adminRoleBtn.textContent.trim()==="Admin"){

        userContainer.classList.add("hidden");
        adminCotnainer.classList.remove("hidden")
        currentRole ="Admin"

    }
})
const DisplayModel = function () {
  showModel.classList.remove("hidden");
  overlay.classList.remove("hidden");
};

const hideModel = function () {
  showModel.classList.add("hidden");
  overlay.classList.add("hidden");
};


updateUserPic.addEventListener("click", function(){
    profileInput.click();
})

profileInput.addEventListener("change",async function(e){
    const file = e.target.files[0];
    console.log(file);
   if(file){
       const formData = new FormData();
       formData.append("image", file);

       console.log(formData);
         await authFetch("http://localhost:8080/api/user/file", {
           method: "POST",
           body: formData
       });

   }
   const user = await authFetch("http://localhost:8080/api/users/me");

    const result =await user.json();
    console.log(result);
    updateUserPic.src = result.avatarUrl;
    updateUserPic.onerror = () => console.log("Image failed to load:", result.avatarUrl);
})




// // role
// adminRoleBtn.addEventListener('click',function(){
//     userContainer.classList.add("hidden");
//     adminCotnainer.classList.remove("hidden")
// })
// userRoleBtn.addEventListener('click',function(){
//     userContainer.classList.remove("hidden");
//     adminCotnainer.classList.add("hidden")
// })

// show task form
addTaskBtn.addEventListener("click", function () {
  DisplayModel();
  taskFormDescription.value = "";
  taskFormDueDate.value = "";
  saveTask.classList.remove("hidden");
  updateTask.classList.add("hidden");
});

//hide the taskform when user click on overlay
overlay.addEventListener("click", hideModel);


///////////////////user container////////////////////////

 async function getPendingTasks(){
    const allTasks =  await authFetch("http://localhost:8080/api/user-tasks");
      return await allTasks.json();

}
// display all pending tasks
getPendingTasks().then(r => r.forEach((task) => {
    console.log(r)
 if(task.taskStatus === "PENDING"){
    console.log(task)
    const updateTaskContainer =`

           <div class="row " data-pending-task-id="${task.taskId}">
              <div class= "my-pending-tasks__data">
              <input type="checkbox" id="completedtask" name="completedtask" />
              </div>
              <div class="my-pending-tasks__data">${task.taskDescription}</div>
              <div class="my-pending-tasks__data">${task.dueDate}</div>

            </div>
       
`

     myPendingTasks.insertAdjacentHTML("beforeend", updateTaskContainer);}

    if(task.taskStatus === "COMPLETED"){

        const updateCompletedContainer = `

         <div class="row" data-completed-task-id="${task.taskId}">
              <div class="my-completed-tasks__data">${task.taskDescription}</div>
              <div class="my-completed-tasks__data">${task.completedDate}</div>

          </div>
          
 `
        myCompletedTasks.insertAdjacentHTML("beforeend", updateCompletedContainer);

    }



}))
async function getProfile(){
    const user = await authFetch("http://localhost:8080/api/users/me");

    return await user.json();

}
getProfile().then(pic=> {

    updateUserPic.src = pic.avatarUrl;
    updateUserPic.onerror = () => console.log("Image failed to load:", pic.avatarUrl);

})


userTaskContainer.addEventListener('click',async function(e) {
    console.log(e.target);
    console.log(myCompletedTasks);
    const target = e.target.matches("#completedtask");
    if (target) {

        const completedTaskId = e.target.closest(".row").dataset.pendingTaskId;


        const statusResponse = await authFetch(`http://localhost:8080/api/update-status/${completedTaskId}`, {
            method: "PATCH",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                taskStatus: "COMPLETED"
            }),
        });
        console.log(statusResponse);
        // get the updated task
        const response = await authFetch(`http://localhost:8080/api/task/${completedTaskId}`);
        const userTask = await response.json();
        console.log(userTask);
        const updateCompletedContainer = `
  
         <div class="row" data-completed-task-id="${userTask.taskId}">
              <div class="my-completed-tasks__data">${userTask.taskDescription}</div>
              <div class="my-completed-tasks__data">${userTask.completedDate}</div>

          </div>
            
 `
        myCompletedTasks.insertAdjacentHTML("beforeend", updateCompletedContainer);

    }








})









/////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////\
// admin container

// displaying all assigned tasks in admin page

async function showAllAssignedTasks() {
  const getTasks = await authFetch("http://localhost:8080/api/tasks")
  const response = await getTasks.json();
  return response;

}
showAllAssignedTasks().then((tasks) => {
  tasks.forEach((task) => {
console.log(task)
    if (task.taskStatus === "PENDING") {
      const html = ` 
 
         <div class="row" data-pending-task-id="${task.taskId}">
                  <div class="user-assigned-tasks__data">${task.taskDescription}</div>
                  <div class="user-assigned-tasks__data">${task.dueDate}</div>
                  <div class="user-assigned-tasks__data">  <img src="${task.profilePicture}" alt="Profile" /></div>
                  <div class="user-assigned-tasks__data"> <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6 editIcon">
                    <path d="M21.731 2.269a2.625 2.625 0 0 0-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 0 0 0-3.712ZM19.513 8.199l-3.712-3.712-12.15 12.15a5.25 5.25 0 0 0-1.32 2.214l-.8 2.685a.75.75 0 0 0 .933.933l2.685-.8a5.25 5.25 0 0 0 2.214-1.32L19.513 8.2Z" />
                  </svg>
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6 deleteIcon">
                      <path fill-rule="evenodd" d="M16.5 4.478v.227a48.816 48.816 0 0 1 3.878.512.75.75 0 1 1-.256 1.478l-.209-.035-1.005 13.07a3 3 0 0 1-2.991 2.77H8.084a3 3 0 0 1-2.991-2.77L4.087 6.66l-.209.035a.75.75 0 0 1-.256-1.478A48.567 48.567 0 0 1 7.5 4.705v-.227c0-1.564 1.213-2.9 2.816-2.951a52.662 52.662 0 0 1 3.369 0c1.603.051 2.815 1.387 2.815 2.951Zm-6.136-1.452a51.196 51.196 0 0 1 3.273 0C14.39 3.05 15 3.684 15 4.478v.113a49.488 49.488 0 0 0-6 0v-.113c0-.794.609-1.428 1.364-1.452Zm-.355 5.945a.75.75 0 1 0-1.5.058l.347 9a.75.75 0 1 0 1.499-.058l-.346-9Zm5.48.058a.75.75 0 1 0-1.498-.058l-.347 9a.75.75 0 0 0 1.5.058l.345-9Z" clip-rule="evenodd" />
                    </svg></div>
                </div>


 `



      taskContainer.insertAdjacentHTML("beforeend", html);
    }


      if(task.taskStatus === "COMPLETED"){
          const html = ` <div class="row" data-task-id="${task.taskId}">
              <div class="user-completed-tasks__data">${task.taskDescription}</div>
              <div class="user-completed-tasks__data">${task.dueDate}</div>
              <div class="user-completed-tasks__data">  <img src="${task.profilePicture}" alt="Profile" /></div>
              <div class="user-completed-tasks__data"> <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6 editIcon">
                <path d="M21.731 2.269a2.625 2.625 0 0 0-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 0 0 0-3.712ZM19.513 8.199l-3.712-3.712-12.15 12.15a5.25 5.25 0 0 0-1.32 2.214l-.8 2.685a.75.75 0 0 0 .933.933l2.685-.8a5.25 5.25 0 0 0 2.214-1.32L19.513 8.2Z" />
              </svg>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6 deleteIcon">
                  <path fill-rule="evenodd" d="M16.5 4.478v.227a48.816 48.816 0 0 1 3.878.512.75.75 0 1 1-.256 1.478l-.209-.035-1.005 13.07a3 3 0 0 1-2.991 2.77H8.084a3 3 0 0 1-2.991-2.77L4.087 6.66l-.209.035a.75.75 0 0 1-.256-1.478A48.567 48.567 0 0 1 7.5 4.705v-.227c0-1.564 1.213-2.9 2.816-2.951a52.662 52.662 0 0 1 3.369 0c1.603.051 2.815 1.387 2.815 2.951Zm-6.136-1.452a51.196 51.196 0 0 1 3.273 0C14.39 3.05 15 3.684 15 4.478v.113a49.488 49.488 0 0 0-6 0v-.113c0-.794.609-1.428 1.364-1.452Zm-.355 5.945a.75.75 0 1 0-1.5.058l.347 9a.75.75 0 1 0 1.499-.058l-.346-9Zm5.48.058a.75.75 0 1 0-1.498-.058l-.347 9a.75.75 0 0 0 1.5.058l.345-9Z" clip-rule="evenodd" />
                </svg></div>
            </div> 
                

 `



          adminCompletedTasksContainer.insertAdjacentHTML("beforeend", html);


      }
  });
});
//////////////////////////////////////////////////////////////
// add new task to the assigned tasks container in admin page
//////////////////////////////////////////////////////////////
saveTask.addEventListener("click", async function (e) {
    e.preventDefault();

    hideModel();

    const task = {
        taskDescription: taskFormDescription.value,
        dueDate: taskFormDueDate.value,
        userId: parseInt(taskAssignedTo.value, 10),
        // taskStatus: "PENDING",
        // completedOn: null,
    };
    //
    console.log(task.dueDate);
    console.log(task);

    //
    async function saveTask() {
        await authFetch("http://localhost:8080/api/tasks", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(task),
        });
        console.log("Saved task");
    }

    const date = new Date();

    const month = String(date.getMonth() + 1).padStart(2, 0);

//
    const CurrentDate = `${date.getFullYear()}-${month}-${date.getDate()}`;

    console.log(date.getDay())
//
//   // const checkIfTaskExist = allTasks.some((oldtask) => {
//   //   return oldtask.dueDate == task.dueDate && oldtask.user == task.user;
//   // });
//
    if (task.dueDate <= CurrentDate) {
        console.error(`please select date after ${CurrentDate}`);
    } else {
        await saveTask();
    }

    const displayTask =`<tr>
                <td class= "user-assigned-tasks__data"> ${task.taskDescription}</td>
                <td class= "user-assigned-tasks__data">${task.dueDate}</td>
                <td class= "user-assigned-tasks__data">
                  <img src="profile.jpg" alt="Profile" />
                </td>
                <td class= "user-assigned-tasks__data">
                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6 editIcon">
                    <path d="M21.731 2.269a2.625 2.625 0 0 0-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 0 0 0-3.712ZM19.513 8.199l-3.712-3.712-12.15 12.15a5.25 5.25 0 0 0-1.32 2.214l-.8 2.685a.75.75 0 0 0 .933.933l2.685-.8a5.25 5.25 0 0 0 2.214-1.32L19.513 8.2Z" />
                  </svg>
                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="size-6 deleteIcon">
                    <path fill-rule="evenodd" d="M16.5 4.478v.227a48.816 48.816 0 0 1 3.878.512.75.75 0 1 1-.256 1.478l-.209-.035-1.005 13.07a3 3 0 0 1-2.991 2.77H8.084a3 3 0 0 1-2.991-2.77L4.087 6.66l-.209.035a.75.75 0 0 1-.256-1.478A48.567 48.567 0 0 1 7.5 4.705v-.227c0-1.564 1.213-2.9 2.816-2.951a52.662 52.662 0 0 1 3.369 0c1.603.051 2.815 1.387 2.815 2.951Zm-6.136-1.452a51.196 51.196 0 0 1 3.273 0C14.39 3.05 15 3.684 15 4.478v.113a49.488 49.488 0 0 0-6 0v-.113c0-.794.609-1.428 1.364-1.452Zm-.355 5.945a.75.75 0 1 0-1.5.058l.347 9a.75.75 0 1 0 1.499-.058l-.346-9Zm5.48.058a.75.75 0 1 0-1.498-.058l-.347 9a.75.75 0 0 0 1.5.058l.345-9Z" clip-rule="evenodd" />
                  </svg>

                </td>


              </tr>
`

 ;

    taskContainer.insertAdjacentHTML("afterbegin", displayTask);


});

closeModel.addEventListener("click", hideModel);


////////////////////////////////////////////////////
// update task
////////////////////////////////////////////////////


document.addEventListener("click", async function (e) {
    const target = e.target;
console.log(target)
    ////////////////////////////////////////////////
    if (target.classList.contains("editIcon")) {
        // show the form
        DisplayModel();

        // switch to update task btn
        saveTask.classList.add("hidden");
        updateTask.classList.remove("hidden");

        const oldTaskContainer = target.closest(".row");
        console.log(oldTaskContainer)
        const taskId = oldTaskContainer.dataset.pendingTaskId;
        console.log(taskId)

        async function getOldTask() {
            const existingTask = await authFetch(`http://localhost:8080/api/task/${taskId}`);

            return await existingTask.json();
        }

/////////////display old task in form fields///////////////////
        getOldTask().then(result => {
                console.log(result)
                taskFormDescription.value = result.taskDescription
                taskFormDueDate.value = result.dueDate
                taskAssignedTo.value = result.userId

            }
        );


//////////////when user clicks update button

        // compare old to new if there are changes
        // add it to json and send it to patch mapping
        updateTask.addEventListener("click", async function (e) {
            e.preventDefault();
            console.log(e.target);

            // const taskId = oldTask.dataset.taskId;
            const newTask = {
                taskDescription: taskFormDescription.value,
                dueDate: taskFormDueDate.value,
                userId: Number(taskAssignedTo.value),

            };

            const oldTask = await getOldTask();
            let updatedTask = {};
            if (newTask.taskDescription !== oldTask.taskDescription) {
                updatedTask.taskDescription = newTask.taskDescription;
            }
            if (newTask.dueDate !== oldTask.dueDate) {
                updatedTask.dueDate = newTask.dueDate;
            }
            if (newTask.userId !== oldTask.userId) {
                updatedTask.userId = newTask.userId;
            }
            console.log("updated task:", updatedTask);


            const response = await authFetch(`http://localhost:8080/api/tasks/${taskId}`, {
                method: "PATCH",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(updatedTask),
            });

            console.log(response);

        });

    }

    if (target.classList.contains("deleteIcon")){

        const oldTaskContainer = target.closest(".row");
        console.log(oldTaskContainer)
        const taskId = oldTaskContainer.dataset.pendingTaskId;
        console.log(taskId)

       const deleteTask = await authFetch(`http://localhost:8080/api/tasks/${taskId}`, {
            method: "DELETE",

        });

        console.log(deleteTask)

    }
})

