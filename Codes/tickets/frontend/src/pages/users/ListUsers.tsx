import AppHeader from "@/components/AppHeader"
import Card from "@/components/Card"
import api from "@/services/api"
import type { UserInterface } from "@/types/users"
import { useEffect, useState } from "react"

const ListUsers = () => {

    // Hook: useState
    const [users, setUsers] = useState<UserInterface[]>([])

    // Hook: useEffect
    useEffect(() => {

        api.get('/api/users')
            .then(response => {
                console.log(response)
                setUsers(response.data)
            })

    }, [])

    return (
        <>
            <AppHeader title="Lista de usuários" />
            <div className="flex flex-wrap justify-center">
                {
                    users.map(user => (
                        <Card 
                            key={user.id} 
                            id={user.id} 
                            name={user.name} 
                            updateUrl={`/users/${user.id}`}
                            deleteUrl={`/users/${user.id}/delete`}
                        
                        />
                    ))
                }
            </div>
        </>
    )

}

export default ListUsers
