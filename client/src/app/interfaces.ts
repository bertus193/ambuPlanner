export interface MyObj {
    nodes: string;
    mapPosition: Array<Node>;
}

interface Node {
    coordValue: CoordValue;
}

interface CoordValue {
    x: number;
    y: number;
    value: string;
}