package advent18

import shared.AdventSolution

fun main() = object : AdventSolution(
        mapOf(
                "abcdef,bababc,abbcde,abcccd,aabcdd,abcdee,ababab" to "4 * 3 = 12"
        ),
        mapOf(
                "abcde,fghij,klmno,pqrst,fguij,axcye,wvxyz" to "fgij"
        ),
        "asgwdcmbrkerohqoutfylvzpnx,asgwjcmbrkejihqoutfylvipne,asgwjcmbrkedihqoutvylizpnz,azgsjcmbrkedihqouifylvzpnx,asgwucmbrktddhqoutfylvzpnx,asgwocmbrkedihqoutfylvzivx,aqgwjcmbrkevihqvutfylvzpnx,tsgljcmbrkedihqourfylvzpnx,asgpjcmbrkedihqoutfnlvzsnx,astwjcmbrktdihqrutfylvzpnx,asgwjcmbrpedhhqoutfylvzynx,xsgwjcmbrkedieqowtfylvzpnx,asgwjcmbvkedihfoutnylvzpnx,asgwjcmtrkedihqouafylvzcnx,asgwjcmbrkedihqoutfylvxpvm,usgwjcmbrkedihqortfyuvzpnx,asgwjcmbrwedihqoutfylizpix,asgrjcvbrkedixqoutfylvzpnx,asgwjcmbrogdihqoutfelvzpnx,aggwjcmbrkesihqoutoylvzpnx,asgtjccbrkedihqoutfrlvzpnx,asgcucmbrbedihqoutfylvzpnx,esgwjcmbrkedihqsutfylvzcnx,asgwjcmbrkedrhqoutfyobzpnx,mngwjcbbrkedihqoutfylvzpnx,asgwjcrbrkeoihqyutfylvzpnx,apgwjcmbrkednhqogtfylvzpnx,asgwjcwbrkedihqoutfylplpnx,asgwjcmbrkfdihqoutfxlvzpyx,aegwjcmbrkedihqoutfylbxpnx,asgljcmbrkedixqoutaylvzpnx,aigwjcmbrkedihqouhfylvzpex,asgwjbmbrkedihqoutfylfzpnp,asgwjcmzroedihqoutcylvzinx,asgwjcwbrieuihqoutfylvzpnx,aagwjcmbrkedjhqdutfylvzpnx,ahgwjcmbrkedihqsutfylvzpfx,asgwjcmbrkedihzosttylvzpnx,aegwjcmbrkedioqnutfylvzpnx,asgwjcmbykidihqoutfysvzpnx,asgwkcxbrkeddhqoutfylvzpnx,ashwjcmbrkeeihqoutfylvzknx,acgwjcmbrqedihqoqtfylvzpnx,asgwjcmtrkedihooutfylszpnx,asgwjcmbrkmdihqfutrylvzpnx,asgwjcmbrkedihqoutjylvapnn,asgwjcmbwkedihqoutkylkzpnx,asgwjrmbrkedihqoutfycnzpnx,asgwtcmbrkedihqoqtfylozpnx,asgajcmbrkedihqoutuylvzpny,asgwjcmbykedihqoutfylfzpwx,asgwjcsbrkedihpoutfylvvpnx,hsdwjcmbrvedihqoutfylvzpnx,asgwjcmbrkedihqoutfdmszpnx,adgwjcmbrtidihqoutfylvzpnx,augwjcmbriedihqoutgylvzpnx,asgwjvmbreedihqoutfllvzpnx,asgwjcnbfkedihqoltfylvzpnx,asgwjcmbykddihqoutqylvzpnx,ajgwjcmbrkedihqoutfylvpvnx,asgwjcmbrkydihqoutfylszpnl,xsgwjcmbrkqdihqoutfylvkpnx,asgwjcmbrkedimqoutfklvzknx,csgwjbmbrkedihqoftfylvzpnx,asgwjcmbjkedihjoutfylvzpnn,asgwjcmprkedihqoulfalvzpnx,asgwjcmbrvediqqoutfyuvzpnx,asgwjambrkedhhqoutkylvzpnx,asgejcmbrkidihqoutfylvzpnk,hsiwjcmbrkedihqoutfylvzpnq,asswjczbrkedihqoutfylczpnx,asgwjnmbrkedyhzoutfylvzpnx,asgwscmbrkedihqoutfklvlpnx,asgwlcmbrktdihqoutfylvzpax,asfwjcmerkedihqoutfylvipnx,asgwjcmbrkeditqoeafylvzpnx,asgwgcmbrkesihqoutfylyzpnx,fsgwjcmbrkedihqouvfyavzpnx,asgwjcmbrpedwhqoutfylmzpnx,asgwjcmbrkzdzhqoucfylvzpnx,asgwjcmnrketmhqoutfylvzpnx,asgwjcmbrkedihxoutsylvzpnh,asgwjcobrkedihqoutfrlvzpox,asgwjcmbrkedihqootfylxzpox,asgjjcmcrkedihqoutfylmzpnx,lsgwjcmbrkedihqoutfyqvzunx,asgwjcmbrwedihqoutoylvzpnu,aszwjcmbtkedihqoutfylczpnx,asgwjcmbykedihqoutfylvgpex,asgijcmbrkedilqoutkylvzpnx,astwxcmzrkedihqoutfylvzpnx,akgwjcmbnkedihqfutfylvzpnx,asgwjcmbrqndivqoutfylvzpnx,asgwjrmbrleqihqoutfylvzpnx,asgwjcmbrkevihqoutfxlvzpvx,asbwjcmbrkedihqoutfelvwpnx,asewjcbbrkmdihqoutfylvzpnx,asgwjcmbrkeaihxoutfylpzpnx,asgwjzmbrkedihqrotfylvzpnx,asgwjcmbrkedihqoutgdxvzpnx,asgwjcwbrkmdihqoutfylvzlnx,asgwjcmbrkegihqoutfylrzpax,ajgwjcmbrkegihqhutfylvzpnx,asgwjcmbrzedihqhutfylvkpnx,asgwjcmwrkedihqouhfylkzpnx,aygwjcmbrkedihqoutfdlvzpnr,asgwjcmbrkednhqoutiylvypnx,aqgwjcmbrkezihqoutfylvzonx,bsgwjcmbrkedihqouhfylvzsnx,asgwjcmcrkedihqokyfylvzpnx,asgsjcmbrkewiyqoutfylvzpnx,asgwpcmbrkejihqoutfylzzpnx,asgwjumbrkedbeqoutfylvzpnx,asgwjcmbrkedihpoutqylqzpnx,awgwjcmbrredihqoutfylvzpna,asgwjsmbraedihqoutfylvzpvx,asgwncmbrkedihqoutfyljzrnx,asgwncmbrkedihqohtfylvzonx,asgwjcmbrkedihqlutfylvypux,asgwjcmbbkedihooutfylkzpnx,asghjcmsryedihqoutfylvzpnx,asgwjcmbrkevihqoulfzlvzpnx,asggjcmbrkedizqoutfylvzknx,asbwjcmbriedihqoutfylvmpnx,asgwjcmbrkedqbqoutfylvzenx,asgwjcmprkedihqoutfylvzknp,asgwjcmbrkerihqoutfwlvzpno,asgwjcmvrkesihqoutrylvzpnx,asgzjcmbrkedihqoutfnlvbpnx,asfwjcmbrkhdihqoutfylpzpnx,asgwjcmbskedihqdutfyyvzpnx,asgwjcmzrkedihqoutcylvzinx,asgwjcmbrkedibqoutfylvjonx,asgwjcmbrbedihqoutfylmzbnx,asgwjcmbrkedhhqoutmylczpnx,asgwjcmbrkbgihqoutzylvzpnx,asgwjcfbrkedihqoupfyxvzpnx,asiwjcmbzkedihqoutfyluzpnx,asvwjcmbrkedihqoitfylvzpns,asgwjcmxikedihqoutfyevzpnx,asgwjcmbrkedioqoutfylvzwox,asgwjcmbrkedivqoutjyuvzpnx,asgwjcmbkkydihqrutfylvzpnx,asgwjcmbrkxdiuqoutfylvopnx,asgwjcmbrkedihqouthylvzpra,asgwjcmbrzedimloutfylvzpnx,asgwjcmbrkedmhqoulfytvzpnx,asgwjcmbrkzdihqrutfysvzpnx,ssgwjcmxrkedihqoutftlvzpnx,asgwjcmbrkedihqoutfajvzynx,asgwjcmbrkqdihqxuufylvzpnx,asmwjcabrkedihqouxfylvzpnx,asgwjcmbrkeeihqoatfycvzpnx,asgwjcjbrgedjhqoutfylvzpnx,asgljcmtrkedihqoutoylvzpnx,asgwjcmbrkedigqouzfylvzpvx,ajgvjcmbkkedihqoutfylvzpnx,asgwjcmbrkedihqtugfygvzpnx,asgbjcmbrkedihboftfylvzpnx,asgwjwmbrkedihqontfylhzpnx,asgwjfmhrkedihqoutfylvqpnx,asgwjxmbrkedihqoutzylvzpnj,asgwjcrlrkedihqoutfylvzpsx,aygwjcmbrkedihqoutsylvzdnx,zsgwjcmbrkedihjogtfylvzpnx,asgwjxmbrkegihqoutfylvopnx,asgwjcmbrkedihqhutfylvzcnr,asgwicmbrkewihvoutfylvzpnx,asqwjcmbvkedihqoutfylvzknx,asgwjcmbrkedihqoktfyevzpnu,asgwjcmbrkudihqoutfylqzznx,asgwjdmbrkedihqoutfylvvdnx,asgwjcmbrkwmihqautfylvzpnx,asgwjcmbrxedihqoctfyldzpnx,asgwjdmbrkedjhqoutfyfvzpnx,asgwjcmtrzedihqoutfylvzpnm,bpgwjcmbrmedihqoutfylvzpnx,asgwjctbrkedihqoqtfynvzpnx,askhjcmbrkedihqoutfylvzrnx,asgkjcmblkehihqoutfylvzpnx,asgwjjmbrkedvhqoutfhlvzpnx,asgwjcmbrkedihqoupzylvzknx,asgwjcmbukedchqoutfylizpnx,askwjcmdrkedihqoutwylvzpnx,asgwjcmbtkcdihloutfylvzpnx,asgwjcmbrkedwgqoutvylvzpnx,asmwjcmbrkedihqoutfylozpnc,asgwjcmbriedibqouofylvzpnx,asgnjcmcrkedihqoupfylvzpnx,asgzjcmbrksdihqoutiylvzpnx,asgwjcrbkkedihqouafylvzpnx,asgwjcmbkkvdihqqutfylvzpnx,astwjcqbrkedihqoutfylvzpvx,asgwjcmhrkehihqoutfylvzvnx,asgwjcmbraeduhqoutmylvzpnx,asgwjcmbrkedihquutnylvzptx,asgwjcmbrkedilqoftfylvzpnz,akgwjmmbrkedihqoutfylxzpnx,asgwjcmbrkhxikqoutfylvzpnx,asgcjcmetkedihqoutfylvzpnx,fsgwjcmsrkedihooutfylvzpnx,gsgwjcmbrkedihdoutfylvzdnx,asgwtccbrkedihqoutfylvwpnx,asuwjcmbrkedihqcutfylvzpox,asgwacmbrkodihqlutfylvzpnx,asgwjcmbrkediuqoutfylqhpnx,asgwjcmbrkwdrhqoutfylvzpno,angwjcsblkedihqoutfylvzpnx,aigwjcmbyoedihqoutfylvzpnx,adgwjcmbrkedihqtutfylyzpnx,asgwjzmbrkeeihqputfylvzpnx,asgwjcmbrkwdihqoutfylvzpwc,asgpjcmbrkgdihqbutfylvzpnx,osgwjmmbrkedijqoutfylvzpnx,asgjjcmbrkkdihqoutfylvzynx,asgwjcnerwedihqoutfylvzpnx,azgwhcmbrkedicqoutfylvzpnx,asnwjcmbrsedihqoutfylvzpnm,hsgwjcmgrkedihqoutfilvzpnx,asgwscmbrkjdihqoutfylvzpnm,asgbjbmbrkedhhqoutfylvzpnx,aswwjcmtrkedihqjutfylvzpnx,asgwicmbrbedihqoutfylvzpnm,asgwjcubrkedihqoutfylvbnnx,asvwjcmbrkehihqoutfylhzpnx,gsgwjcmbrkedihqoutsklvzpnx,asgwjcubikedihqoitfylvzpnx,asgwjpmbskedilqoutfylvzpnx,aigwjcmbrxedihqoutyylvzpnx,asgwjcpbrkedihxoutfynvzpnx,asgwjcmbrkegihqoutfklvzcnx,asgwjvubrkedjhqoutfylvzpnx,asgwjcabrkedihqoutfyivzplx,asgwjcxbrkedihqgutfylvepnx,asgwlcmbrkedihqoutqylvwpnx,asgwjhmbrkydihqhutfylvzpnx,asgwjcmbrkedihqoutfylwzone,asgwycmbrkadihqoutuylvzpnx,asgwjcybrkedihqoftfylvzpne,asgwjcmnrkedihrodtfylvzpnx,asgwicmwrkedihqoutfyovzpnx,aqgwjlmbrkedilqoutfylvzpnx,asgwjcmzskwdihqoutfylvzpnx,asgwjcmdrkebihqoutfylvjpnx,asgwjcmbrkpdihqoutfylxzphx,asgwjcmbrkedixqoutlylfzpnx,asgwjcmbrkadihqoutfylvlpdx,asgejcmqrkedyhqoutfylvzpnx,asgwjcmvroedihpoutfylvzpnx,asgwjcmxrkedihqoutfyivzpmx"
) {

    override fun solveProblem1(input: String): String {
        var count1 = 0
        var count2 = 0
        input.split(',').forEach { str ->
            val counts = str.groupBy { it }.values.map { it.size }.toSet()
            if(counts.contains(2)) count1++
            if(counts.contains(3)) count2++
        }
        return "$count1 * $count2 = ${count1*count2}"
    }

    override fun solveProblem2(input: String): String {
        val ids = input.split(',')
        ids.forEach { id1 ->
            ids.forEach lookForMatch@{ id2 ->
                if(id1 == id2) return@lookForMatch
                val diff = id1.mapIndexed { idx, ch ->
                   if(ch == id2[idx]) ch.toString() else ""
                }.joinToString("")
                if(diff.length == id1.length -1)
                    return@solveProblem2 diff
            }
        }
        return ""
    }

}.solve()

