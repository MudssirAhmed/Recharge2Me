package com.recharge2mePlay.recharge2me.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textfield.TextInputEditText
import com.recharge2mePlay.recharge2me.R

class MontserratBoldTextView : AppCompatTextView {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START

        face = ResourcesCompat.getFont(context, R.font.montserrat_bold)!!
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = ResourcesCompat.getFont(context, R.font.montserrat_bold)!!
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
            context,
            attrs,
            defStyle
    ) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_bold)!!
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}

class MontserratMediumTextView : AppCompatTextView {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_medium)!!
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = ResourcesCompat.getFont(context, R.font.montserrat_medium)!!
        this.typeface = face
        this.gravity = Gravity.START
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_medium)!!
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}

class MontserratSemiBoldTextView : AppCompatTextView {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_semi_bold)!!
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = ResourcesCompat.getFont(context, R.font.montserrat_semi_bold)!!
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_semi_bold)!!
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}

class MontserratLightTextView : AppCompatTextView {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_light)!!
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = ResourcesCompat.getFont(context, R.font.montserrat_light)!!
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
            context,
            attrs,
            defStyle
    ) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_light)!!
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}

class MontserratRegularTextView : AppCompatTextView {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_regular)!!
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = ResourcesCompat.getFont(context, R.font.montserrat_regular)!!
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
            context,
            attrs,
            defStyle
    ) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_regular)!!
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}

class MontserratBoldEditText : AppCompatEditText {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_bold)!!
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = ResourcesCompat.getFont(context, R.font.montserrat_bold)!!
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
            context,
            attrs,
            defStyle
    ) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_bold)!!
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}

open class MontserratMediumEditText : AppCompatEditText {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_medium)!!
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = ResourcesCompat.getFont(context, R.font.montserrat_medium)!!
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_medium)!!
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}

class MontserratSemiBoldEditText : AppCompatEditText {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_semi_bold)!!
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = ResourcesCompat.getFont(context, R.font.montserrat_semi_bold)!!
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_semi_bold)!!
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}

class MontserratMediumButton : AppCompatButton {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_medium)!!
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = ResourcesCompat.getFont(context, R.font.montserrat_medium)!!
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_medium)!!
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}

class MontserratMediumTextInputEditText : TextInputEditText {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_medium)!!
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = ResourcesCompat.getFont(context, R.font.montserrat_medium)!!
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.context = context
        this.gravity = Gravity.START
        face = ResourcesCompat.getFont(context, R.font.montserrat_medium)!!
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}

class MontserratSemiBoldSwitchCompat @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = R.attr.switchStyle
) : SwitchCompat(context, attrs, defStyleAttr) {
    init {
        /*        // Retrieve the custom attribute value
                val a = context.obtainStyledAttributes(attrs, R.styleable.CustomSwitchCompat)
                val customAttrValue = a.getBoolean(R.styleable.CustomSwitchCompat_customAttr, false)
                a.recycle()

                // Use the custom attribute value to customize the switch
                isChecked = customAttrValue
                trackTintList = ColorStateList.valueOf(Color.RED)*/

        this.typeface = ResourcesCompat.getFont(context, R.font.montserrat_semi_bold)!!
    }
}