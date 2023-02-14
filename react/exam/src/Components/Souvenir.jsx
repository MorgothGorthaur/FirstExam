import {Button} from "react-bootstrap";

const Souvenir = ({souvenir, remove}) => {
    return (
        <div>
            <h1> {souvenir.name} </h1>
            <h1> {souvenir.date} </h1>
            <h1> {souvenir.price} </h1>
            <Button variant={"danger"} onClick={() => remove(souvenir.id)}> remove </Button>
        </div>
    );
};
export default Souvenir;