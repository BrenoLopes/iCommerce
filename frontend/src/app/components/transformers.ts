
export const transformPriceIntoString: (p: number) => string = (price) => {
  const formatter = new Intl.NumberFormat('pt-BR', {
    currency: 'BRL',
    style: 'currency',
    maximumFractionDigits: 2,
    minimumFractionDigits: 2
  })

  return formatter.format(price) + ""
}
