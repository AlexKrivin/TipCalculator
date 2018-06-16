// TipCalculator.java
// Вычисляет чаевые на основе счета с 5, 10, 15 или
// введенной пользователем процентной ставки.
package com.example.alex_k.TipCalculator;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

// Главный класс Activity для приложения TipCalculator
public class TipCalculatorTest extends Activity
{
    // константы, используемые при сохранении/восстановлении состояния
    private static final String   BILL_TOTAL = "BILL_TOTAL";
    private static final String   CUSTOM_PERCENT = "CUSTOM_PERCENT";
    private              double   currentBillTotal;                               // счет, вводимый пользователем
    private              int      currentCustomPercent;                           // % чаевых, выбранный SeekBar
    private              EditText tip10EditText;                                  // 10%-чаевые
    private              EditText total10EditText;                                // общий счет, включая 10%-чаевые
    private              EditText tip15EditText;                                  // 15%-чаевые
    private              EditText total15EditText;                                // общий счет, включая 15%-чаевые
    private              EditText billEditText;                                   // ввод счета пользователем
    private              EditText tip20EditText;                                  // 20%-чаевые
    private              EditText total20EditText;                                // общий счет, включая 20%-чаевые
    private              TextView customTipTextView;                              // % пользовательских чаевых
    private              EditText tipCustomEditText;                              // пользовательские чаевые
    private              EditText totalCustomEditText;                            // общий счет

    // Вызывается при первом создании класса activity.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);       // вызов версии суперкласса
        setContentView(R.layout.mainactivity);    // «раздувание» GUI
        // Приложение запущено впервые или восстановлено из памяти?
        if ( savedInstanceState == null ) // приложение запущено впервые
        {
            currentBillTotal = 0.0;               // инициализация суммы счета нулем
            currentCustomPercent = 18;            // инициализация пользовательских чаевых значением 18 %
        } // конец структуры if
        else // приложение восстановлено из памяти
        {
            // инициализация суммы счета сохраненной в памяти суммой
            currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL);
            // инициализация пользовательских чаевых сохраненным процентом чаевых
            currentCustomPercent = savedInstanceState.getInt(CUSTOM_PERCENT);
        } // конец структуры else

        // ссылки на чаевые 10 %, 15 %, 20 % и итоговые EditTexts
        tip10EditText = (EditText) findViewById(R.id.tip10EditText);
        total10EditText = (EditText) findViewById(R.id.total10EditText);
        tip15EditText = (EditText) findViewById(R.id.tip15EditText);
        total15EditText = (EditText) findViewById(R.id.total15EditText);
        tip20EditText = (EditText) findViewById(R.id.tip20EditText);
        total20EditText = (EditText) findViewById(R.id.total20EditText);
        // TextView, отображающий процент пользовательских чаевых
        customTipTextView = (TextView) findViewById(R.id.customTipTextView);
        // пользовательские чаевые и итоговые EditTexts
        tipCustomEditText = (EditText) findViewById(R.id.tipCustomEditText);
        totalCustomEditText = (EditText) findViewById(R.id.totalCustomEditText);
        // получение billEditText
        billEditText = (EditText) findViewById(R.id.billEditText);
        // billEditTextWatcher обрабатывает событие onTextChanged из billEditText
        billEditText.addTextChangedListener(billEditTextWatcher);
        // получение SeekBar, используемого для подсчета суммы пользовательских чаевых
        SeekBar customSeekBar = (SeekBar) findViewById(R.id.seekBar);
        customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
    } // конец метода onCreate

    // вычисляет чаевые, хранящиеся в EditTexts, по ставкам 10, 15 и 20 %
    private void updateStandard()
    {
        // вычисляет итоговый счет, включающий чаевые со ставкой 10 %
        double tenPercentTip = currentBillTotal * .1;
        double tenPercentTotal = currentBillTotal + tenPercentTip;
        // настройка текста tipTenEditText в соответствии с tenPercentTip
        tip10EditText.setText(String.format(" %.02f", tenPercentTip));
        // настройка текста totalTenEditText в соответствии с tenPercentTotal
        total10EditText.setText(String.format(" %.02f", tenPercentTotal));
        // вычисление общего итога с чаевыми 15 %
        double fifteenPercentTip = currentBillTotal * .15;
        double fifteenPercentTotal = currentBillTotal + fifteenPercentTip;
        // настройка текста tipFifteenEditText в соответствии с fifteenPercentTip
        tip15EditText.setText(String.format(" %.02f", fifteenPercentTip));
        // настройка текста totalFifteenEditText в соответствии с fifteenPercentTotal
        total15EditText.setText(String.format(" %.02f", fifteenPercentTotal));
        // вычисление общего итога с чаевыми 20 %
        double twentyPercentTip = currentBillTotal * .20;
        double twentyPercentTotal = currentBillTotal + twentyPercentTip;
        // настройка текста tipTwentyEditText в соответствии с twentyPercentTip
        tip20EditText.setText(String.format(" %.02f", twentyPercentTip));
        // настройка текста totalTwentyEditText в соответствии с twentyPercentTotal
        total20EditText.setText(String.format(" %.02f", twentyPercentTotal));

    } // конец метода updateStandard

    // обновляет компоненты EditText, включающие пользовательские чаевые и итоги
    private void updateCustom()
    {
        // настройка текста customTipTextView в соответствии с положением SeekBar
        customTipTextView.setText(currentCustomPercent + " %");
        // вычисление суммы пользовательских чаевых
        double customTipAmount = currentBillTotal * currentCustomPercent * .01;
        // вычисление итогового счета, включая пользовательские чаевые
        double customTotalAmount = currentBillTotal + customTipAmount;
        // отображение суммы чаевых и итогового счета
        tipCustomEditText.setText(String.format(" %.02f", customTipAmount));
        totalCustomEditText.setText(String.format(" %.02f", customTotalAmount));
    } // конец метода updateCustom

    // сохранение значений billEditText и customSeekBar
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putDouble( BILL_TOTAL, currentBillTotal );
        outState.putInt( CUSTOM_PERCENT, currentCustomPercent );
    } // конец метода onSaveInstanceState

    // вызывается при изменении пользователем положения ползунка SeekBar
    private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener()
    {
        // обновление currentCustomPercent, потом вызов updateCustom
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            // присваивание currentCustomPercent положения ползунка SeekBar
            currentCustomPercent = seekBar.getProgress();
            // обновление EditTexts пользовательскими чаевыми и итоговыми счетами
            updateCustom();
        } // конец метода onProgressChanged

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {
        } // конец метода onStartTrackingTouch
        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
        } // конец метода onStopTrackingTouch

    }; // конец OnSeekBarChangeListener

    // объект обработки событий, реагирующий на события billEditText
    private TextWatcher billEditTextWatcher = new TextWatcher()
    {
        // вызывается после ввода пользователем числа
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            // преобразование текста billEditText в double
            try
            {
                currentBillTotal = Double.parseDouble(s.toString());
            } // завершение блока try

            catch (NumberFormatException e)
            {
                // по умолчанию в случае исключения
                currentBillTotal = 0.0;
            } // завершение блока catch

            // обновление EditTexts, содержащих стандартные и пользовательские чаевые
            updateStandard(); // обновление 10, 15 и 20 % EditTexts
            updateCustom();   // обновление EditText с пользовательскими чаевыми
        } // конец метода onTextChanged

        @Override
        public void afterTextChanged(Editable s)
        {
        } // конец метода afterTextChanged

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
        } // завершение метода beforeTextChanged

    }; // завершение billEditTextWatcher

} // завершение класса TipCalculator


