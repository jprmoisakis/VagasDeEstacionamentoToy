package exemplodaauladetestes

class Vaga {
    String descricao
    boolean ocupada
    static hasMany = [historicoDeReservas:Reserva]
    Date dataEntrada
    double tempoOcupacao

    Vaga() {
        historicoDeReservas = []
        tempoOcupacao = 0
    }

    def select() {
        if (ocupada) {
            def reserva = new Reserva(entrada: dataEntrada, saida: new Date(), vaga:this)
            historicoDeReservas.add(reserva)
            tempoOcupacao = tempoOcupacao + (reserva.saida.time - reserva.entrada.time)
            dataEntrada = null
        } else {
            dataEntrada = new Date()
        }
        ocupada = !ocupada
    }

    static constraints = {
        descricao blank: false
        dataEntrada nullable: true
    }

    @Override
    String toString() {
        return "<" + descricao + ", " + ocupada + " >"
    }

    def tempoOcupacaoDHMS() {   //https://www.mkyong.com/java/java-time-elapsed-in-days-hours-minutes-seconds/
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        def tempo = tempoOcupacao;
        long elapsedDays = tempo / daysInMilli;
        tempo = tempo % daysInMilli;
        long elapsedHours = tempo / hoursInMilli;
        tempo = tempo % hoursInMilli;
        long elapsedMinutes = tempo / minutesInMilli;
        tempo = tempo % minutesInMilli;
        long elapsedSeconds = tempo / secondsInMilli;
        [days:elapsedDays,hours:elapsedHours,minutes:elapsedMinutes,seconds:elapsedSeconds]
    }
}
