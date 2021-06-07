import React, {useRef, useState} from "react"
import { parse } from 'date-fns'
import { validatePredicates } from "../validators";
// @ts-ignore
import {useDateInput} from "@use-date-input/core"
// @ts-ignore
import {adapter as dateAdapter} from '@use-date-input/date-fns-adapter'

import "./CalendarInputComponent.scss"

interface Props {
  label: string,
  validators: InputPredicate[],
  onChange: (text: string, isValid: boolean) => void,
  type: FormInputType,
}

interface TextInputState {
  errorMessages: string[],
  value: string
}

const CalendarInputComponent: React.FC<Props> = (props: Props) => {
  const defaultParseDate = (value: string) => parse(value, 'dd/MM/yyyy', new Date())
  const actions = useRef()

  const [showCalendarPicker, setShowCalendarPicker] = useState(false)
  const [date, setDate] = useState('')
  const [state, setState] = useState<TextInputState>({
    errorMessages: [],
    value: ""
  })

  const onCalendarChange = (newSelectedDate: any) => {
    // @ts-ignore
    const { dateAPI } = actions.current
    setDate(dateAPI.format(newSelectedDate, 'dd/MM/yyyy'))

    setShowCalendarPicker(false)
  }

  const inputClassName: string
    = `input-element ${state.errorMessages.length > 0 ? "border-red" : ""}`

  const onInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setDate(event.target.value)
    checkValidity(event.target.value)
  }

  const onFocus = (event: React.FocusEvent<HTMLInputElement>) => {
    checkValidity(event.target.value)
  }

  const checkValidity = (text: string) => {
    const result = validatePredicates(props.validators, text)

    props.onChange(text, result.isValid)
    setState({value: text, errorMessages: result.errorMessages})
  }

  const {
    Calendar,
    CalendarProvider,
    getCalendarProviderProps,
    getInputProps
  } = useDateInput({ actions, parse: defaultParseDate })

  return (
    <div className="calendar-container">
      <div className="input-label">{props.label}</div>
      <div className="calendar-input">
        <input
          className={inputClassName}
          {...getInputProps({ onChange: onInputChange, onFocus: onFocus })}
          value={date}
        />
        <div className="calendar-toggle">
          <button onClick={() => setShowCalendarPicker(!showCalendarPicker)}>
            <i className="fas fa-calendar-alt" />
          </button>
          <div className={`calendar-picker ${showCalendarPicker ? "": "hide"}`}>
            <CalendarProvider
              {...getCalendarProviderProps({ adapter: dateAdapter, onCalendarChange: onCalendarChange })}
            >
              <Calendar />
            </CalendarProvider>
          </div>
        </div>
      </div>
      <div className="input-errors-container">
        {state.errorMessages.map(error => <p className="input-error">{error}</p>)}
      </div>
    </div>
  )
}

export default CalendarInputComponent
