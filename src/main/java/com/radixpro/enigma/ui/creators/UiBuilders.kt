/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label

const val styleSheet = "css/enigma.css"

/**
 * Creates a Label, based on the Builder pattern.
 */
class LabelBuilder(private val text: String) {
    private var prefWidth = 0.0
    private var prefHeight = 0.0
    private var layoutX = 0.0
    private var layoutY = 0.0
    private var alignment: Pos? = null
    private var styleClass: String? = null

    fun setPrefWidth(prefWidth: Double): LabelBuilder {
        this.prefWidth = prefWidth
        return this
    }

    fun setPrefHeight(prefHeight: Double): LabelBuilder {
        this.prefHeight = prefHeight
        return this
    }

    fun setStyleClass(styleClass: String): LabelBuilder {
        this.styleClass = styleClass
        return this
    }

    fun setAlignment(alignment: Pos): LabelBuilder {
        this.alignment = alignment
        return this
    }

    fun build(): Label {
        val lblText: String = if (text.isNotEmpty()) text else ""
        val label = Label(lblText)
        if (prefWidth > 0.0) label.prefWidth = prefWidth
        if (prefHeight > 0.0) label.prefHeight = prefHeight
        if (layoutX > 0.0) label.layoutX = layoutX
        if (layoutY > 0.0) label.layoutY = layoutY
        if (null != styleClass && styleClass!!.isNotBlank()) label.styleClass.add(styleClass)
        if (null != alignment) label.alignment = alignment
        return label
    }
}


/**
 * Creates a Button, based on the Builder pattern.
 */
class ButtonBuilder(private val text: String) {
    private var disabled = false
    private var focusTraversable = false
    private var prefWidth = 0.0

    fun setPrefWidth(width: Double): ButtonBuilder {
        this.prefWidth = width
        return this
    }

    fun setDisabled(disabled: Boolean): ButtonBuilder {
        this.disabled = disabled
        return this
    }

    fun setFocusTraversable(focusTraversable: Boolean): ButtonBuilder {
        this.focusTraversable = focusTraversable
        return this
    }

    fun build(): Button {
        val button = Button(text)
        button.isDisable = disabled
        button.isFocusTraversable = focusTraversable
        if (prefWidth > 1.0) button.prefWidth = prefWidth
        return button
    }
}