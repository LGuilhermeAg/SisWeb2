import { Link } from "react-router-dom"

const Menu = () => {

    const cssMenu = "text-sm font-medium text-slate-600 transition-colors hover:text-indigo-600"

    return (
        <nav className="flex gap-4">
            <Link to="/" className={cssMenu}>Home</Link>
            <Link to="/users" className={cssMenu}>Usuários</Link>
            <Link to="#" className={cssMenu}>Tickets</Link>
            <Link to="#" className={cssMenu}>Perfil</Link>
            <Link to="#" className={cssMenu}>Relatórios</Link>
        </nav>
    )


}

export default Menu