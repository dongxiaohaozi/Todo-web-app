export const fetch_get_tasks = () => {
    console.log("get into fetch get");
     return fetch("http://localhost:8081/tasks")
    .then(res => res.json())
}

export const fetch_post_task = (data) => {
    console.log(data)
     return fetch("http://localhost:8081/tasks", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then((res) => res.json())
         .catch(err => console.log(err))

}

export function fetch_delete_task(id) {
    fetch(`http://localhost:8081/tasks/${id}`, {
        method: 'DELETE',
    })
}

export function fetch_change_status(task) {
    return fetch(`http://localhost:8081/tasks/${task.id}`, {
        method: 'PUT',
        body: JSON.stringify(task),
        headers: {'Content-Type': 'application/json'}
    }).then(res => res.json())
}
