import react from 'react';
import SeparatorComponent from "../separator/SeparatorComponent";

import "./ProductThumbnailComponent.scss"
import {transformPriceIntoString} from "../transformers";

interface Props {
  imageUrl: string,
  productName: string,
  productPrice: number,
  onClick: () => void,
}

const ProductThumbnailComponent: react.FC<Props> = (props: Props) => {
  const price = transformPriceIntoString(props.productPrice)

  return (
    <div className="product-thumbnail" role="button" onClick={props.onClick}>
      <div className="product-image">
        <img
          src={props.imageUrl}
          alt="what"
        />
      </div>
      <SeparatorComponent label="" color="dark" />
      <div className="product-info">
        <div className="product-name">
          {props.productName}
        </div>
        <div className="product-price">
          <p>Por</p>
          <h1>{price}</h1>
        </div>
      </div>
    </div>
  )
}

export default ProductThumbnailComponent
