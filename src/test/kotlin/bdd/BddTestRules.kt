package bdd

import io.cucumber.java8.En
import io.cucumber.java8.PendingException

sealed class Register
object Accumulator : Register()
object ProgramCounter : Register()
object X : Register()
object Y : Register()
object PC: Register()

enum class Flag(val mnemonic: String, val bitMask: UInt) {
    C("Carry", 0x01u),
    Z("Zero", 0x02u),
    I("Interrupt", 0x04u),
    D("Decimal", 0x08u),
    B("Break", 0x10u),
    U("Unused", 0x20u),
    V("Overflow", 0x40u),
    N("Negative", 0x80u)
}

@Suppress("unused")
class BddRules : En {

    // Where should I put this?
    private var lastInstance: String? = null

    // Part of the following nonsense comes from another project I did NEP a Nes emulator
    init {
        ParameterType("register", "Accumulator|PC|X|Y") { register ->
            when (register) {
                "PC" -> ProgramCounter
                "Accumulator" -> Accumulator
                "X" -> X
                "Y" -> Y
                else -> throw PendingException()
            }
        }

        ParameterType("flag", "C|Z|I|D|B|U|V|N") { flag ->

            when (flag) {
                "C" -> Flag.C
                "Z" -> Flag.Z
                "I" -> Flag.I
                "D" -> Flag.D
                "B" -> Flag.B
                "U" -> Flag.U
                "V" -> Flag.V
                "N" -> Flag.N
                else -> throw PendingException()
            }
        }

        ParameterType("binOrHex", "0[xX][0-9a-fA-F]+|0[bB][0-1]+") { num ->
            when {
                num.lowercase().startsWith("0x") -> Integer.decode(num)
                num.lowercase().startsWith("0b") -> 0
                else -> throw PendingException()
            }
        }

        ParameterType("address", "0[xX][0-9a-fA-F]+") { address -> Integer.decode(address) }

        ParameterType("enableDisable", "ENABLED|DISABLED") { value ->
            when (value) {
                "ENABLED" -> true
                "DISABLED" -> false
                else -> throw PendingException()
            }
        }

        When("code is executed") {
            checkNotNull(lastInstance) { "I need to have everything in place before executing" }
//            lastInstance!!.runTest()
        }

        Given("ROM memory {string}") { str: String ->
            val mem = str.split(' ').map { Integer.decode(it).toUByte() }.toUByteArray()
            lastInstance = "Something"
        }

        And("{register} register is {binOrHex}") { register: Register, num: Int ->
            checkNotNull(lastInstance)
        }

        And("{flag} CPU flag is {enableDisable}") { flag: Flag, be: Boolean ->
            checkNotNull(lastInstance)
        }

        And("{binOrHex} is stored at address {binOrHex}") { data: Int, address: Int ->
            checkNotNull(lastInstance)
        }

        Then("{register} register should be {binOrHex}") { register: Register, num: Int ->
            checkNotNull(lastInstance)
        }

        And("{flag} flag should be {enableDisable}") { flag: Flag, be: Boolean ->
            checkNotNull(lastInstance)
        }

        And("{register} should be {enableDisable}") { register: Register, be: Boolean ->
            checkNotNull(lastInstance)
        }

        Then("address {address} should contain {binOrHex}") { address: Int, contain: Int ->
            checkNotNull(lastInstance)
        }

        And("CPU should have performed {int} cycles") { cycles: Int ->
            checkNotNull(lastInstance)
        }
    }
}
